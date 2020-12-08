package com.common.curd.commoncurd.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:26:34
 * @Desc
 */
public class CommonConstant {

    //简单sql防注入字段
    public static final List<String> sqlList = Lists.newArrayList("truncate",
            "insert",
            "delete",
            "update ",
            "declare",
            "alter",
            "drop",
            ";");
    //公共参数过滤字段
    public static final List keyList = Lists.newArrayList("viewName","curPage","recordPerPage","orderCol");
}

	