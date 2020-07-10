package com.common.curd.commoncurd.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseUtil {

    /**
     *
     * @Title: successResult
     * @Description: 统一接口成功返回
     * @param @param response
     * @param @param result    参数
     * @return void    返回类型
     * @throws
     */
    public static void successResult(HttpServletResponse response, Object result) {
        responseJson(response,result);
    }
    private static void responseJson(HttpServletResponse response, Object result) {
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
        String json = JsonUtil.getGson().toJson(result);
        OutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(json.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        }
    }
}
