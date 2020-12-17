package com.common.curd.commoncurd.model;

import java.util.HashMap;
import java.util.List;

/**
 * company XXX
 *
 * @author cuihui
 * @date 2020年7月13日下午12:34:56
 * @Desc 分页类
 */
public class Page<T> extends HashMap<String, Object> {

    /**
     * 每页最大数据条数
     */
    public static Integer MAX_RECORD_PER_PAGE = 10000;

    /**
     * 每页默认的数据条数
     */
    private int recordPerPage = 30;

    /**
     * 当前页
     */
    private int curPage = 1;

    /**
     * 页数
     */
    private int pageNumbers;

    /**
     * 数据条数
     */
    private long recordNumbers;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 升序/降序
     */
    private String sortOrder;

    private Object param;

    /**
     * 结果数据
     */
    private List<T> data;

    public int getRecordPerPage() {
        try {
            recordPerPage = this.get("recordPerPage") == null ? this.recordPerPage : Integer.parseInt(this.get("recordPerPage") + "");
        } catch (Exception e) {
            // 默认30条
            recordPerPage = 30;
        }

        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
        this.put("recordPerPage", recordPerPage);
    }

    public int getCurPage() {
        try {
            curPage = this.get("curPage") == null ? this.curPage : Integer.parseInt(this.get("curPage") + "");
        } catch (Exception e) {
            // 默认第一页
            curPage = 1;
        }
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
        this.put("curPage", curPage);
    }

    public int getPageNumbers() {
        try {
            pageNumbers = Integer.parseInt(this.get("pageNumbers") + "");
        } catch (Exception e) {
            pageNumbers = 0;
        }
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
        this.put("pageNumbers", pageNumbers);
    }

    public long getRecordNumbers() {
        try {
            recordNumbers = Integer.parseInt(this.get("recordNumbers") + "");
        } catch (Exception e) {
            pageNumbers = 0;
        }
        return recordNumbers;
    }

    public void setRecordNumbers(long recordNumbers) {

        this.recordNumbers = recordNumbers;
        this.put("recordNumbers", recordNumbers);
    }

    public List<T> getData() {
        this.data = (List<T>) this.get("data");
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.put("data", data);
    }

    public Object getParam() {
        this.param = this.get("param");
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
        this.put("param", param);
    }

    public String getSortName() {
        this.sortName = this.get("sortName") == null ? "" : this.get("sortName") + "";
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
        this.put("sortName", sortName);
    }

    public String getSortOrder() {
        sortOrder = this.get("sortOrder") == null ? "" : this.get("sortOrder") + "";
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        this.put("sortOrder", sortOrder);
    }
}

