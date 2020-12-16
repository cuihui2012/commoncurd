package com.common.curd.commoncurd.model;

import java.io.Serializable;

public class IPConfigVo implements Serializable {
    /**
     * 主键ID
     */
    private String iid;

    /**
     * ip地址
     */
    private String ip_address;

    /**
     * 应用ip描述
     */
    private String ip_desc;

    /**
     * 使用标志 0-未使用 1-在用
     */
    private String is_used;

    /**
     * 开放标志 0-视图权限控制 1-全局开放
     */
    private String is_opend;

    /**
     * 更新时间
     */
    private String update_time;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getIp_desc() {
        return ip_desc;
    }

    public void setIp_desc(String ip_desc) {
        this.ip_desc = ip_desc;
    }

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }

    public String getIs_opend() {
        return is_opend;
    }

    public void setIs_opend(String is_opend) {
        this.is_opend = is_opend;
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
        return "IPConfigVo{" +
                "iid='" + iid + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", ip_desc='" + ip_desc + '\'' +
                ", is_used='" + is_used + '\'' +
                ", is_opend='" + is_opend + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
