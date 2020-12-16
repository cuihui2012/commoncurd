package com.common.curd.commoncurd.dao;
import com.common.curd.commoncurd.model.Page;
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
public interface ICommonApiDao {

	public String getViewNameInfo(String viewName);
	public Map<String,Object> getColNameInfo(@Param("viewName") String viewName, @Param("colName") String colName);
	public List<Map<String,Object>> getDataByViewName(Page page);

	public String getTableNameInfo(@Param("tableName") String tableName);
	public String getSeqNameInfo(@Param("seqName") String seqName);
	public String getNextSeqValue(@Param("seqName") String seqName);
	public void addOrUpdateDataByTableNames(Map<String,Object> paramMap);

	public void softDeleteDataByTableName(Map<String,Object> paramMap);
	public void hardDeleteDataByTableName(Map<String,Object> paramMap);

	public Map<String,Object> getExternalUrlInfo(Map<String,Object> paramMap);

	public void insertRequestLogInfo(Map<String,Object> paramMap);
}