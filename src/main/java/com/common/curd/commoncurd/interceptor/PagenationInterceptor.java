package com.common.curd.commoncurd.interceptor;

import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.utils.ReflectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * company xxx
 *
 * @author cuihui
 * @date 2019年7月13日下午12:34:10
 * @Desc mybatis 分页插件
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
public class PagenationInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(PagenationInterceptor.class);
    /**
     * 数据库类型 不同数据库不同的分页
     */
    private String dbType;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        if (obj instanceof Page<?>) {
            Page<?> page = (Page<?>) obj;
            MappedStatement mappedStatement =
                    (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            Connection connection = (Connection) invocation.getArgs()[0];

            String sql = boundSql.getSql();
            this.setTotalRecord(page, mappedStatement, connection);
            String pageSql = this.getPageSql(page, sql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }


    @Override
    public void setProperties(Properties properties) {
        this.dbType = properties.getProperty("dbType");
    }


    private String getPageSql(Page<?> page, String sql) {
        if ("mysql".equalsIgnoreCase(this.dbType)) {
            return getMysqlPageSql(page, sql);
        } else if ("oracle".equalsIgnoreCase(this.dbType)) {
            return getOraclePageSql(page, sql);
        }
        return sql;
    }


    /**
     * @param
     * @return
     * @author cuihui
     * @Date: 2020年3月6日 下午4:14:27
     * @desc 获取Mysql数据库的分页查询语句
     */

    private String getMysqlPageSql(Page<?> page, String sql) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        // 指定了页数按优先按页数查询 若没指定按起始位置查询
        StringBuffer sqlBuffer = new StringBuffer(sql);
        int offset = page.getCurPage() - 1 > 0 ? (page.getCurPage() - 1) * page.getRecordPerPage() + 1 : 1;
        sqlBuffer.append(" limit ").append(offset).append(" , ").append(page.getRecordPerPage());
        return sqlBuffer.toString();
    }


    /**
     * * 获取Oracle数据库的分页查询语句 * @param page 分页对象 
     * * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * * @return Oracle数据库的分页查询语句       
     */
    private String getOraclePageSql(Page<?> page, String sql) {
        // 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = page.getCurPage() - 1 > 0 ? (page.getCurPage() - 1) * page.getRecordPerPage() + 1 : 1;
        StringBuffer pageSql = new StringBuffer();
        pageSql.append(" SELECT * FROM (");
        pageSql.append("    SELECT A.*,ROWNUM RN FROM ( ");
        pageSql.append(sql);
        if (!StringUtils.isEmpty(page.getSortName())) {
            // 子查询里面包含order by的情况
            if (!sql.toLowerCase().contains("order by")) {
                pageSql.append(" order by ");
                pageSql.append(page.getSortName()).append(" ").append(page.getSortOrder());
            }
        }
        pageSql.append(" ) A ");
        pageSql.append("WHERE ROWNUM < " + (offset + page.getRecordPerPage()) + ") B WHERE B.RN >= " + offset + "");
        return pageSql.toString();
    }


    /**
     * * 给当前的参数对象page设置总记录数
     * * @param page Mapper映射语句对应的参数对象 
     * * @param mappedStatement Mapper映射语句 
     * * @param connection 当前的数据库连接       
     */
    private void setTotalRecord(Page<?> page, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql =
                new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        // 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        // 通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                // 总记录数
                page.setRecordNumbers(rs.getInt(1));
            }
        } catch (SQLException e) {
            logger.error("PageInterceptor sql error", e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                logger.error("PageInterceptor sql error", e2);
            }
        }
    }


    /**
     * * 根据原Sql语句获取对应的查询总记录数的Sql语句
     */
    private String getCountSql(String sql) {
        return "SELECT COUNT(1) from (" + sql + ") temp";
    }

}
