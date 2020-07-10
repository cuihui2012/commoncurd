package com.common.curd.commoncurd.utils;

import com.google.gson.Gson;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:27:04
 * @Desc
 */
public class JsonUtil {
	
	private static Gson gson = new Gson();
	
	private JsonUtil(){}
	
	public static Gson getGson(){
		return gson;
	}
}
