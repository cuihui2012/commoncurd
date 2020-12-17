package com.common.curd.commoncurd.interceptor;

import com.alibaba.fastjson.JSON;
import com.common.curd.commoncurd.service.ICommonApiService;
import com.common.curd.commoncurd.utils.IPUtil;
import com.common.curd.commoncurd.utils.ObjectValuedUtil;
import com.common.curd.commoncurd.utils.RequestThreadLocal;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    // POST请求body参数
    private String param;

    // 访问IP
    private String clientIP;

    @Resource
    private ICommonApiService commonApiService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.error("公共拦截器.......");

        // 获取IP地址
        clientIP = IPUtil.getClientIp(request);

        // 处理post请求body参数
        param = getBodyString(request.getReader());
        Gson gson = new Gson();
        Map<String,Object> postParam = gson.fromJson(param, HashMap.class);
        RequestThreadLocal.setPostRequestParams(postParam);

        // 允许跨域处理
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragmal", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min

        // 授权通过
        return true;
    }


    /**
     * 在Controller方法后进行拦截
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 保存请求日志
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ip_address", clientIP);
        String url_path = request.getRequestURL().toString();
        String uri_path = url_path.substring(url_path.lastIndexOf("/")+1);
        paramMap.put("url_path",url_path );
        paramMap.put("uri_path", uri_path);
        paramMap.put("method", request.getMethod());
        // 链接后缀参数
        paramMap.put("param_follow", request.getQueryString());
        // 所有参数
        Map<String, Object> param_all = new HashMap<>();
        ObjectValuedUtil.setObjectValue(param_all, request);
        paramMap.put("param_all", JSON.toJSONString(param_all));
        commonApiService.insertRequestLogInfo(paramMap);
    }

    /**
     * DispatcherServlet进行视图的渲染之后进行拦截
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取request请求body中参数
     * @param br
     * @return
     */
    private String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
