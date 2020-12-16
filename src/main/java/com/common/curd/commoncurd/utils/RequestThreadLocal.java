package com.common.curd.commoncurd.utils;

import java.util.Map;

public class RequestThreadLocal {
	private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();
	
	 public static Map<String,Object> getPostRequestParams() {
	        return threadLocal.get();
	 }
	 
	 public static void setPostRequestParams(Map<String,Object> postRequestParams){
	        threadLocal.set(postRequestParams);
	 }
}
