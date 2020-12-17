package com.common.curd.commoncurd.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseUtil {

    /**
     * @param @param response
     * @param @param result    参数
     * @return void    返回类型
     * @throws
     * @Title: successResult
     * @Description: 统一接口成功返回
     */
    public static void successResult(HttpServletResponse response, Object result) {
        String json = JsonUtil.getGson().toJson(result);
        OutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(json.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
