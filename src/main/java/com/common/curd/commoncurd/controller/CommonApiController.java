package com.common.curd.commoncurd.controller;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.service.ICommonApiService;
import com.common.curd.commoncurd.utils.ObjectValuedUtil;
import com.common.curd.commoncurd.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * company xxx
 *
 * @author cuihui
 * @date 2019年9月10日下午3:06:53
 * @Desc
 */
@Controller
@RequestMapping("/common/")
public class CommonApiController{

    private static Logger logger = LoggerFactory.getLogger(CommonApiController.class);

    @Resource
    public ICommonApiService commonApiService;

    /**
     * 通过视图名称获取数据
     *
     * @param request
     * @param response
     */
    @RequestMapping("getDataByViewName")
    public void getDataByViewName(HttpServletRequest request, HttpServletResponse response) {
        Page page = new Page<>();
        Result result = new Result();
        ObjectValuedUtil.setObjectValue(page, request);

        try {
            result = commonApiService.getDataByViewName(page);
            page = (Page) result.getData();
            page.wrapToPage();
            result.setDesc("查询成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("查询失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("查询失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }

    /**
     * 通过表名新增或修改数据(支持事务)
     * @param request
     * @param response
     */
    @RequestMapping("addOrUpdateDataByTableNames")
    public void addOrUpdateDataByTableNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> paramMap = new HashMap<>();
        Result result = new Result();
        ObjectValuedUtil.setObjectValue(paramMap,request);
        try {
            commonApiService.addOrUpdateDataByTableNames(paramMap);
            result.setDesc("更新成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        }catch (Exception e){
            logger.error("更新失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("更新失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response,result);
    }

    /**
     * 通过表名删除数据
     * @param request
     * @param response
     */
    @RequestMapping("deleteDataByTableNames")
    public void deleteDataByTableNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> paramMap = new HashMap<>();
        Result result = new Result();
        ObjectValuedUtil.setObjectValue(paramMap,request);
        try {
            commonApiService.deleteDataByTableNames(paramMap);
            result.setDesc("删除成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        }catch (Exception e){
            logger.error("删除失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("删除失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response,result);
    }

    /**
     * 通过表名增、删、改数据
     * @param request
     * @param response
     */
    @RequestMapping("addOrUpdateOrDeleteDataByTableNames")
    public void addOrUpdateOrDeleteDataByTableNames(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> paramMap = new HashMap<>();
        Result result = new Result();
        ObjectValuedUtil.setObjectValue(paramMap,request);
        try {
            commonApiService.addOrUpdateOrDeleteDataByTableNames(paramMap);
            result.setDesc("操作成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        }catch (Exception e){
            logger.error("操作失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("操作失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response,result);
    }

    /**
     *  三方接口调用
     * @param request
     * @param response
     */
    @RequestMapping("executeExternalAPI")
    public void executeExternalAPI(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> paramMap = new HashMap<>();
        Result result = new Result();
        ObjectValuedUtil.setObjectValue(paramMap,request);
        //校验必填项
        if(StringUtils.isBlank((String)paramMap.get("urlID"))) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("urlID必填");
            ResponseUtil.successResult(response,result);
            return;
        }
        try {
            result = commonApiService.executeExternalAPI(paramMap);
            result.setDesc("操作成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        }catch (Exception e){
            logger.error("操作失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("操作失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response,result);
    }
}