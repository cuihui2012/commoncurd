package com.common.curd.commoncurd.controller;

import com.common.curd.commoncurd.constant.ResultCodeConstant;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.service.ICommonApiAuthService;
import com.common.curd.commoncurd.service.ICommonApiLogService;
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
@RequestMapping("/log/")
public class CommonApiLogController {

    private static Logger logger = LoggerFactory.getLogger(CommonApiLogController.class);

    @Resource
    public ICommonApiLogService commonApiLogService;

    /**
     * 获取api日志列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("getApiLogList")
    public void getApiLogList(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Page page = new Page<>();
        ObjectValuedUtil.setObjectValue(page, request);
        try {
            result = commonApiLogService.getApiLogList(page);
            result.setDesc("查询成功!");
            result.setCode(ResultCodeConstant.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("查询失败", e);
            result.setCode(ResultCodeConstant.FAILURE_CODE);
            result.setDesc("查询失败！" + e.getMessage());
        }
        ResponseUtil.successResult(response, result);
    }
}