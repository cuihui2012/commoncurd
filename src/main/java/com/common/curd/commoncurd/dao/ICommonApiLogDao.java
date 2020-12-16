package com.common.curd.commoncurd.dao;

import com.common.curd.commoncurd.model.*;
import java.util.List;

/**
 *
 * company xxx
 * @author cuihui
 * @date  2019年7月3日
 *
 */
public interface ICommonApiLogDao {
	public List<ApiLogVo> getApiLogList(Page page);
}