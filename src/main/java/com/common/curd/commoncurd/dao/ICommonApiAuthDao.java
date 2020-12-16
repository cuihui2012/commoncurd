package com.common.curd.commoncurd.dao;

import com.common.curd.commoncurd.model.IPConfigVo;
import com.common.curd.commoncurd.model.IPViewRelaVo;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.ViewColumnRelaVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 *
 * company xxx
 * @author cuihui
 * @date  2019年7月3日
 *
 */
public interface ICommonApiAuthDao {
	public IPConfigVo getIPConfigInfo(@Param("ipAddress") String ipAddress);
	public List<IPConfigVo> getIPConfigList(Page page);
	public void addOrUpdateIPConfigInfo(Map<String, String> paramMap);
	public void deleteIPConfigInfo(@Param("iid") String iid);

	public List<IPViewRelaVo> getIPViewList(Page page);
	public void deleteViewConfigInfo(@Param("vid") String vid);
	public void addViewConfigInfo(Map<String, Object> paramMap);

	public List<ViewColumnRelaVo> getViewColumnList(@Param("view_name") String view_name, @Param("vid") String vid);
	public void addOrUpdateColumnConfigInfo(Map<String, String> paramMap);

	public List<String> getAllViewList(Page page);

	public String getColumnsByIPAndViewName(@Param("ipAddress") String ipAddress, @Param("viewName") String viewName);
}