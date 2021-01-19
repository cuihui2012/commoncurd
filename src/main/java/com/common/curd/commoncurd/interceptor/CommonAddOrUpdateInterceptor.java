package com.common.curd.commoncurd.interceptor;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.dao.ICommonApiAuthDao;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonAddOrUpdateInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(CommonAddOrUpdateInterceptor.class);

    @Resource
    public ICommonApiAuthDao commonApiAuthDao;

    // 访问IP
    private String clientIP;

    // 请求参数
    private Map<String, Object> paramMap = new HashMap<>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Result result = new Result();

        logger.error("公共增加/修改拦截器.......");
        // 获取IP地址
        clientIP = IPUtil.getClientIp(request);
        logger.error("clientIP----------------->" + clientIP);

        // 获取请求参数
        ObjectValuedUtil.setObjectValue(paramMap, request);
        List<Map<String,Object>> paramList = (List<Map<String, Object>>) paramMap.get("paramList");
        for (int i = 0; i < paramList.size(); i++) {
            Map<String,Object> tableMap = paramList.get(i);
            String tableName = (String) tableMap.get("tableName");
            Map<String,Object> cols = (Map<String, Object>) tableMap.get("cols");
            Set<String> keySet = cols.keySet();
            for (String key : keySet) {
                String columnAuthFlag = commonApiAuthDao.getColumnAuthFlag(clientIP, tableName, key);
                // 增加/修改操作字段必须有C权限
                if (!"C".equalsIgnoreCase(columnAuthFlag)) {
                    result.setCode(ResultCodeConstant.FAILURE_CODE);
                    result.setDesc("字段未授权！" + tableName + ":" + key);
                    ResponseUtil.successResult(response, result);
                    return false;
                }
            }
        }
        // 授权通过
        return true;
    }


    /**
     * 在Controller方法后进行拦截
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * DispatcherServlet进行视图的渲染之后进行拦截
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
