package com.common.curd.commoncurd.controller;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.service.ICommonApiAuthService;
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
@RequestMapping("/auth/")
public class CommonApiAuthController {

    private static Logger logger = LoggerFactory.getLogger(CommonApiAuthController.class);

    @Resource
    public ICommonApiAuthService commonApiAuthService;

    /**
     * 获取ip配置详情
     *
     * @param request
     * @param response
     */
    @RequestMapping("getIPConfigInfo")
    public void getIPConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String ipAddress = request.getParameter("ipAddress");
        if (StringUtils.isEmpty(ipAddress)) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("ipAddress字段不能为空");
            ResponseUtil.successResult(response, result);
            return;
        }
        try {
            result = commonApiAuthService.getIPConfigInfo(ipAddress);
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
     * 获取ip配置列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("getIPConfigList")
    public void getIPConfigList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Page page = new Page<>();
        ObjectValuedUtil.setObjectValue(page, request);
        try {
            result = commonApiAuthService.getIPConfigList(page);
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
     * 新增/修改ip配置信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("addOrUpdateIPConfigInfo")
    public void addOrUpdateIPConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Map<String, String> paramMap = new HashMap();
        ObjectValuedUtil.setObjectValue(paramMap, request);
        try {
            commonApiAuthService.addOrUpdateIPConfigInfo(paramMap);
            result.setDesc("更新成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("更新失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("更新失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }

    /**
     * 级联删除ip配置信息
     * @param request
     * @param response
     */
    @RequestMapping("deleteIPConfigInfo")
    public void deleteIPConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String iid = request.getParameter("iid");
        if (StringUtils.isEmpty(iid)) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("iid字段不能为空");
            ResponseUtil.successResult(response, result);
            return;
        }
        try {
            commonApiAuthService.deleteIPConfigInfo(iid);
            result.setDesc("删除成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("删除失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("删除失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }

    /**
     * 根据IP主键获取视图列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("getIPViewList")
    public void getIPViewList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Page page = new Page<>();
        ObjectValuedUtil.setObjectValue(page, request);
        try {
            result = commonApiAuthService.getIPViewList(page);
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
     * 级联删除视图配置信息
     * @param request
     * @param response
     */
    @RequestMapping("deleteViewConfigInfo")
    public void deleteViewConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String vid = request.getParameter("vid");
        if (StringUtils.isEmpty(vid)) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("vid字段不能为空");
            ResponseUtil.successResult(response, result);
            return;
        }
        try {
            commonApiAuthService.deleteViewConfigInfo(vid);
            result.setDesc("删除成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("删除失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("删除失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }

    /**
     * 根据视图获取字段列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("getViewColumnList")
    public void getViewColumnList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String view_name = request.getParameter("view_name");
        String vid = request.getParameter("vid");
        if (StringUtils.isEmpty(view_name)) {
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("view_name字段不能为空");
            ResponseUtil.successResult(response, result);
            return;
        }
        try {
            result = commonApiAuthService.getViewColumnList(view_name, vid);
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
     * 删除字段配置信息
     * @param request
     * @param response
     */
    @RequestMapping("addOrUpdateColumnConfigInfo")
    public void addOrUpdateColumnConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Map<String, String> paramMap = new HashMap();
        ObjectValuedUtil.setObjectValue(paramMap, request);
        try {
            commonApiAuthService.addOrUpdateColumnConfigInfo(paramMap);
            result.setDesc("删除成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("删除失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("删除失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }

    /**
     * 获取所有视图列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("getAllViewList")
    public void getAllViewList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Page page = new Page<>();
        ObjectValuedUtil.setObjectValue(page, request);
        try {
            result = commonApiAuthService.getAllViewList(page);
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
     * 新增视图授权信息(含字段授权)
     *
     * @param request
     * @param response
     */
    @RequestMapping("addViewConfigInfo")
    public void addViewConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Map<String, Object> paramMap = new HashMap<>();
        ObjectValuedUtil.setObjectValue(paramMap, request);

        try {
            commonApiAuthService.addViewConfigInfo(paramMap);
            result.setDesc("新增成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("新增失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("新增失败！核对视图是否重复新增");
        }
        ResponseUtil.successResult(response, result);
    }
}