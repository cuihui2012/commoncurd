package com.common.curd.commoncurd.model;

import com.common.curd.commoncurd.utils.ObjectValuedUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * company 正元智慧
 *
 * @author cwt
 * @date 2018年7月13日下午12:34:56
 * @Desc 分页类
 */
public class Page<T> extends HashMap<String, Object> {
    /**
     *
     */
    private static final long serialVersionUID = 3947564364677248170L;

    public static Integer MAX_RECORD_PER_PAGE = 10000;
    /**
     * 每页的数据条数
     */
    private int recordPerPage = 3000;

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

    private String ltSortName;

    private String ltSortOrder;

    private Object param;

    private List<T> data;


    /**
     * 从request中创建分页对象
     *
     * @param request
     * @param c
     * @return
     */
    public static final <S> Page<S> buildFromRequest(HttpServletRequest request, Class<S> c) {
        Page<S> page = new Page<S>();
        ObjectValuedUtil.setObjectValue(page, request);
        return page;
    }

    public void wrapToMap() {
        this.put("recordPerPage", recordPerPage);
        this.put("curPage", curPage);
        pageNumbers = (int) (recordNumbers / recordPerPage);
        if (recordNumbers % recordPerPage != 0) {
            pageNumbers++;
        }
        this.put("pageNumbers", pageNumbers);
        this.put("recordNumbers", recordNumbers);
        this.put("data", data);
    }

    public void wrapToPage() {
        getCurPage();
        getData();
        getLtSortName();
        getLtSortOrder();
        getPageNumbers();
        getRecordNumbers();
        getParam();
        getRecordPerPage();

    }


    public int getRecordPerPage() {
        try {
            recordPerPage = Integer.parseInt(this.get("recordPerPage") + "");
        } catch (Exception e) {
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
            curPage = Integer.parseInt(this.get("curPage") + "");
        } catch (Exception e) {
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
            pageNumbers = 30;
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
        param = this.put("param", param);
        this.param = param;
    }

    public String getLtSortName() {
        ltSortName = this.get("lt_sort_name") == null ? "" : this.get("lt_sort_name") + "";
        return ltSortName;
    }

    public void setLtSortName(String ltSortName) {
        this.ltSortName = ltSortName.replace("'", null);
        this.put("lt_sort_name", ltSortName);
    }

    public String getLtSortOrder() {
        ltSortOrder = this.get("lt_sort_order") == null ? "" : this.get("lt_sort_order") + "";
        return ltSortOrder;
    }

    public void setLtSortOrder(String ltSortOrder) {
        this.ltSortOrder = ltSortOrder.replace("'", null);
        this.put("lt_sort_order", ltSortOrder);
    }
//	@Override
//	 public Set<Map.Entry<String,Object>> entrySet() {
//	    customAttrs=FiledsUtils.getObjectValue(this, "customAttrs");
//	    System.out.println("customAttrs length is"+customAttrs.size());
//	    Iterator<String> it =customAttrs.keySet().iterator();
//	    while (it.hasNext()) {
//             System.out.println(it.next());
//
//        }
//        return super.entrySet();
//    }

}

