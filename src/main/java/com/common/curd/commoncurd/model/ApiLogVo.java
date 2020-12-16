package com.common.curd.commoncurd.model;

import java.io.Serializable;

public class ApiLogVo implements Serializable {
    /**
     * 主键ID
     */
    private String lid;

    /**
     * ip地址
     */
    private String ip_address;

    /**
     * uri地址
     */
    private String uri_path;

    /**
     * url地址
     */
    private String url_path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 链接后缀参数
     */
    private String param_follow;

    /**
     * 完整参数
     */
    private String param_all;

    /**
     * 更新时间
     */
    private String update_time;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getUri_path() {
        return uri_path;
    }

    public void setUri_path(String uri_path) {
        this.uri_path = uri_path;
    }

    public String getUrl_path() {
        return url_path;
    }

    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam_follow() {
        return param_follow;
    }

    public void setParam_follow(String param_follow) {
        this.param_follow = param_follow;
    }

    public String getParam_all() {
        return param_all;
    }

    public void setParam_all(String param_all) {
        this.param_all = param_all;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        if (update_time.contains(".")){
            update_time = update_time.substring(0, update_time.indexOf("."));
        }
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "ApiLogVo{" +
                "lid='" + lid + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", uri_path='" + uri_path + '\'' +
                ", url_path='" + url_path + '\'' +
                ", method='" + method + '\'' +
                ", param_follow='" + param_follow + '\'' +
                ", param_all='" + param_all + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
