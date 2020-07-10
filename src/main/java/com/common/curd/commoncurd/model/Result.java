package com.common.curd.commoncurd.model;

import java.io.Serializable;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:34:46
 * @Desc  响应封装类
 */
public class Result implements Serializable{
	private static final long serialVersionUID = 6816969540290341828L;
    /**
     * 业务码
     */
    private String code = "0000";
    /**
     * 业务描述
     */
    private String desc = "";
    /**
     * 业务数据
     */
    private Object data = null;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    
}	