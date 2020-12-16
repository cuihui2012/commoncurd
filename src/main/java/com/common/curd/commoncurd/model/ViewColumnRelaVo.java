package com.common.curd.commoncurd.model;

import java.io.Serializable;

public class ViewColumnRelaVo implements Serializable {
    /**
     * 列主键ID
     */
    private String cid;

    /**
     * 视图主键ID
     */
    private String vid;

    /**
     * 列名称
     */
    private String column_name;

    /**
     * 视图名称
     */
    private String view_name;

    /**
     * 权限标志
     */
    private String auth_flag;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 字段描述
     */
    private String column_desc;

    /**
     * 字段类型
     */
    private String data_type;

    /**
     * 字段长度
     */
    private String data_length;

    /**
     * 可为空标志
     */
    private String nullable;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getView_name() {
        return view_name;
    }

    public void setView_name(String view_name) {
        this.view_name = view_name;
    }

    public String getAuth_flag() {
        return auth_flag;
    }

    public void setAuth_flag(String auth_flag) {
        this.auth_flag = auth_flag;
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

    public String getColumn_desc() {
        return column_desc;
    }

    public void setColumn_desc(String column_desc) {
        this.column_desc = column_desc;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getData_length() {
        return data_length;
    }

    public void setData_length(String data_length) {
        this.data_length = data_length;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "ViewColumnRelaVo{" +
                "cid='" + cid + '\'' +
                ", vid='" + vid + '\'' +
                ", column_name='" + column_name + '\'' +
                ", view_name='" + view_name + '\'' +
                ", auth_flag='" + auth_flag + '\'' +
                ", update_time='" + update_time + '\'' +
                ", column_desc='" + column_desc + '\'' +
                ", data_type='" + data_type + '\'' +
                ", data_length='" + data_length + '\'' +
                ", nullable='" + nullable + '\'' +
                '}';
    }
}
