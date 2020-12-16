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
public interface ICommonApiAuthService {
	public Result getIPConfigInfo(String ipAddress);
	public Result getIPConfigList(Page page);
	public void addOrUpdateIPConfigInfo(Map<String, String> paramMap);
	public void deleteIPConfigInfo(String iid);

	public Result getIPViewList(Page page);
	public void deleteViewConfigInfo(String vid);
	public void addViewConfigInfo(Map<String, Object> paramMap);

	public Result getViewColumnList(String view_name, String vid);
	public void addOrUpdateColumnConfigInfo(Map<String, String> paramMap);

	public Result getAllViewList(Page page);
}
