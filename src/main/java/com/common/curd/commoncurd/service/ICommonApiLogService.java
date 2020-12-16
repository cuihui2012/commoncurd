package com.common.curd.commoncurd.service;

import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;

import java.util.Map;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:25:55
 *
 */
public interface ICommonApiLogService {
	public Result getApiLogList(Page page);
}
