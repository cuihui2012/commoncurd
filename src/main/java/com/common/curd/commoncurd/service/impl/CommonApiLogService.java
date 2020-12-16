package com.common.curd.commoncurd.service.impl;

import com.common.curd.commoncurd.dao.ICommonApiLogDao;
import com.common.curd.commoncurd.model.*;
import com.common.curd.commoncurd.service.ICommonApiLogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:25:24
 *
 */
@Service
public class CommonApiLogService implements ICommonApiLogService {

	@Resource
	public ICommonApiLogDao commonApiLogDao;

	@Override
	public Result getApiLogList(Page page) {
		Result result = new Result();
		List<ApiLogVo> ipConfigList = commonApiLogDao.getApiLogList(page);
		page.setData(ipConfigList);
		result.setData(page);
		return result;
	}
}
