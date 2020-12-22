package com.common.curd.commoncurd.interceptor;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.dao.ICommonApiAuthDao;
import com.common.curd.commoncurd.dao.ICommonApiDao;
import com.common.curd.commoncurd.model.IPConfigVo;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonSelectInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(CommonSelectInterceptor.class);

    @Resource
    public ICommonApiAuthDao commonApiAuthDao;

    @Resource
    public ICommonApiDao commonApiDao;

    // 访问IP
    private String clientIP;

    // 请求参数
    private Map<String, Object> paramMap = new HashMap<>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Result result = new Result();

        logger.error("公共查询拦截器.......");

        // 获取IP地址
        clientIP = IPUtil.getClientIp(request);
        logger.error("clientIP----------------->" + clientIP);
        // 获取IP配置信息
        IPConfigVo ipConfigVo = commonApiAuthDao.getIPConfigInfo(clientIP);
        // 查看ip是否可用(适配一键禁用IP)
        if (ipConfigVo == null || "0".equals(ipConfigVo.getIs_used())) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("IP未授权");
            ResponseUtil.successResult(response, result);
            return false;
        }

        // 获取请求参数
        ObjectValuedUtil.setObjectValue(paramMap, request);

        //校验视图名称
        Object viewName = paramMap.get("viewName");
        if (viewName== null) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("视图名称必填(viewName)");
            ResponseUtil.successResult(response, result);
            return false;
        }
        //校验视图是否存在
        String realViewName = Base64Util.getFromBase64((String) viewName);
        String count = commonApiDao.getViewNameInfo(realViewName);
        if ("0".equals(count)){
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("视图名称非法");
            ResponseUtil.successResult(response, result);
            return false;
        }
        //校验每页最大数
        Object recordPerPage = paramMap.get("recordPerPage");
        if (recordPerPage != null) {
            if(Integer.valueOf((String) recordPerPage) > Page.MAX_RECORD_PER_PAGE) {
                result.setCode(ResultCodeConstant.FAILURE_CODE);
                result.setDesc("超过最大recordPerPage[10000]");
                ResponseUtil.successResult(response, result);
                return false;
            }
        }

        Object condition = paramMap.get("condition");
        Object key = paramMap.get("key");
        // 校验参数合法化
        if (condition != null){
            if (key == null || (key != null && !Md5Util.getMd5_16((String) condition).equals(StringUtils.upperCase((String) key)))){
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
            String columns = commonApiAuthDao.getColumnsByIPAndViewName(clientIP, realViewName);
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
