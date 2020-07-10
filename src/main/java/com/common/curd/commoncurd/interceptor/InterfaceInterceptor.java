package com.common.curd.commoncurd.interceptor;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.service.ICommonApiService;
import com.common.curd.commoncurd.utils.RequestThreadLocal;
import com.common.curd.commoncurd.utils.ResponseUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterfaceInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(InterfaceInterceptor.class);

    @Value("${IPWhiteList}")
    private String IPWhiteList;

    @Resource
    public ICommonApiService commonApiService;

    // 访问IP
    private String clientIP;

    // POST请求body参数
    private String param;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Result result = new Result();

        // 处理post请求body参数
        param = getBodyString(request.getReader());
        Gson gson = new Gson();
        Map<String,Object> postParam = gson.fromJson(param, HashMap.class);
        RequestThreadLocal.setPostRequestParams(postParam);

        // 校验ip白名单
        clientIP = getClientIp(request);
        if (IPWhiteList.indexOf(clientIP) == -1) {
            logger.error("IP访问限制---------->" + clientIP);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("IP访问限制");
            ResponseUtil.successResult(response, result);
            return false;
        }

        // 授权通过
        return true;
    }


    /**
     * 在Controller方法后进行拦截
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 保存访问ip
        commonApiService.collectRemoteAddr(clientIP);
    }

    /**
     * DispatcherServlet进行视图的渲染之后进行拦截
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
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
