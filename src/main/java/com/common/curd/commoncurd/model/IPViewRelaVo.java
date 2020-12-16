package com.common.curd.commoncurd.model;

import java.io.Serializable;

public class IPViewRelaVo implements Serializable {
    /**
     * 主键ID
     */
    private String vid;

    /**
     * ip配置外键id
     */
    private String iid;

    /**
     * ip地址
     */
    private String ip_address;

    /**
     * 视图名称
     */
    private String view_name;

    /**
     * 试图描述
     */
    private String view_desc;

    /**
     * 更新时间
     */
    private String update_time;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getView_name() {
        return view_name;
    }

    public void setView_name(String view_name) {
        this.view_name = view_name;
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

    public String getView_desc() {
        return view_desc;
    }

    public void setView_desc(String view_desc) {
        this.view_desc = view_desc;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    @Override
    public String toString() {
        return "IPViewRelaVo{" +
                "vid='" + vid + '\'' +
                ", iid='" + iid + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", view_name='" + view_name + '\'' +
                ", view_desc='" + view_desc + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
