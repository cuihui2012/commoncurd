package com.common.curd.commoncurd.interceptor;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.dao.ICommonApiAuthDao;
import com.common.curd.commoncurd.dao.ICommonApiDao;
import com.common.curd.commoncurd.model.IPConfigVo;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.utils.Base64Util;
import com.common.curd.commoncurd.utils.IPUtil;
import com.common.curd.commoncurd.utils.Md5Util;
import com.common.curd.commoncurd.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonSelectInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(CommonSelectInterceptor.class);

    @Resource
    public ICommonApiAuthDao commonApiAuthDao;

    @Resource
    public ICommonApiDao commonApiDao;

    // 访问IP
    private String clientIP;

    // 视图名称
    private String viewName;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Result result = new Result();

        logger.error("公共查询拦截器.......");

        // 获取IP地址
        clientIP = IPUtil.getClientIp(request);
        // 获取IP配置信息
        IPConfigVo ipConfigVo = commonApiAuthDao.getIPConfigInfo(clientIP);
        // 查看ip是否可用(适配一键禁用IP)
        if (ipConfigVo == null || "0".equals(ipConfigVo.getIs_used())) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("IP未授权");
            ResponseUtil.successResult(response, result);
            return false;
        }

        //校验视图名称
        if (StringUtils.isEmpty(request.getParameter("viewName"))) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("视图名称必填(viewName)");
            ResponseUtil.successResult(response, result);
            return false;
        }
        //校验视图是否存在
        viewName = Base64Util.getFromBase64(request.getParameter("viewName"));
        String count = commonApiDao.getViewNameInfo(viewName);
        if ("0".equals(count)){
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("视图名称非法");
            ResponseUtil.successResult(response, result);
            return false;
        }
        //校验每页最大数
        String recordPerPageStr = request.getParameter("recordPerPage");
        if (!StringUtils.isEmpty(recordPerPageStr)) {
            if(Integer.valueOf(recordPerPageStr) > Page.MAX_RECORD_PER_PAGE) {
                result.setCode(ResultCodeConstant.FAILURE_CODE);
                result.setDesc("超过最大recordPerPage[10000]");
                ResponseUtil.successResult(response, result);
                return false;
            }
        }

        String condition = request.getParameter("condition");
        String key = request.getParameter("key");
        // 校验参数合法化
        if (!StringUtils.isEmpty(condition)){
            if (StringUtils.isEmpty(key) || (!StringUtils.isEmpty(key) && !Md5Util.getMd5_16(condition).equals(StringUtils.upperCase(key)))){
                result.setCode(ResultCodeConstant.FAILURE_CODE);
                result.setDesc("condition参数不合法");
                ResponseUtil.successResult(response, result);
                return false;
            }
        }

        // ip免校验
        if ("1".equals(ipConfigVo.getIs_opend())){
            request.setAttribute("selects", " * ");
        }else{
            String columns = commonApiAuthDao.getColumnsByIPAndViewName(clientIP, viewName);
            if (StringUtils.isEmpty(columns)){
                result.setCode(ResultCodeConstant.FAILURE_CODE);
                result.setDesc("数据未授权");
                ResponseUtil.successResult(response, result);
                return false;
            }else{
                request.setAttribute("selects", " "+ columns +" ");
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
