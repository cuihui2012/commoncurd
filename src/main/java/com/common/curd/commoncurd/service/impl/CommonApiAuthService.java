package com.common.curd.commoncurd.service.impl;

import com.common.curd.commoncurd.dao.ICommonApiAuthDao;
import com.common.curd.commoncurd.model.*;
import com.common.curd.commoncurd.service.ICommonApiAuthService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:25:24
 *
 */
@Service
public class CommonApiAuthService implements ICommonApiAuthService {

	@Resource
	public ICommonApiAuthDao commonApiAuthDao;


	@Override
	public Result getIPConfigInfo(String ipAddress) {
		Result result = new Result();
		IPConfigVo ipConfigInfo = commonApiAuthDao.getIPConfigInfo(ipAddress);
		result.setData(ipConfigInfo);
		return result;
	}

	@Override
	public Result getIPConfigList(Page page) {
		Result result = new Result();
		List<IPConfigVo> ipConfigList = commonApiAuthDao.getIPConfigList(page);
		page.setData(ipConfigList);
		result.setData(page);
		return result;
	}

	@Override
	public void addOrUpdateIPConfigInfo(Map<String, String> paramMap) {
		commonApiAuthDao.addOrUpdateIPConfigInfo(paramMap);
	}

	@Override
	public void deleteIPConfigInfo(String iid) {
		commonApiAuthDao.deleteIPConfigInfo(iid);
	}

	@Override
	public Result getIPViewList(Page page) {
		Result result = new Result();
		List<IPViewRelaVo> ipConfigList = commonApiAuthDao.getIPViewList(page);
		page.setData(ipConfigList);
		result.setData(page);
		return result;
	}

	@Override
	public void deleteViewConfigInfo(String vid) {
		commonApiAuthDao.deleteViewConfigInfo(vid);
	}

	@Override
	public void addViewConfigInfo(Map<String, Object> paramMap) {
		commonApiAuthDao.addViewConfigInfo(paramMap);
	}

	@Override
	public Result getViewColumnList(String view_name, String vid) {
		Result result = new Result();
		List<ViewColumnRelaVo> ipConfigList = commonApiAuthDao.getViewColumnList(view_name, vid);
		result.setData(ipConfigList);
		return result;
	}

	@Override
	public void addOrUpdateColumnConfigInfo(Map<String, String> paramMap) {
		commonApiAuthDao.addOrUpdateColumnConfigInfo(paramMap);
	}

	@Override
	public Result getAllViewList(Page page) {
		Result result = new Result();
		List<String> ipConfigList = commonApiAuthDao.getAllViewList(page);
		page.setData(ipConfigList);
		result.setData(page);
		return result;
	}
}
