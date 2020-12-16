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
public interface ICommonApiService {
	public Result getDataByViewName(Page page) throws Exception;
	public void addOrUpdateDataByTableNames(Map<String,Object> paramMap) throws Exception;
	public void deleteDataByTableNames(Map<String,Object> paramMap) throws Exception;
	public void addOrUpdateOrDeleteDataByTableNames(Map<String,Object> paramMap) throws Exception;
	public Result executeExternalAPI(Map<String,Object> paramMap) throws Exception;

	public void insertRequestLogInfo(Map<String,Object> paramMap);
}
