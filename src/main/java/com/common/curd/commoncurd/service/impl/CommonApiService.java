package com.common.curd.commoncurd.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.curd.commoncurd.constant.CommonConstant;
import com.common.curd.commoncurd.dao.ICommonApiDao;
import com.common.curd.commoncurd.model.Page;
import com.common.curd.commoncurd.model.Result;
import com.common.curd.commoncurd.service.ICommonApiService;
import com.common.curd.commoncurd.utils.Base64Util;
import com.common.curd.commoncurd.utils.CommonApiUtil;
import com.common.curd.commoncurd.utils.Md5Util;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * company xxx
 * @author cuihui
 * @date  2019年7月13日下午12:25:24
 *
 */
@Service
public class CommonApiService implements ICommonApiService {

	@Resource
	public ICommonApiDao commonApiDao;

	@Override
	public Result getDataByViewName(Page page) throws Exception {
		Result result = new Result();
		//校验视图是否存在
		String viewName = Base64Util.getFromBase64((String) page.get("viewName"));
		String count = commonApiDao.getViewNameInfo(viewName);
		// dblink访问表不进行表名校验
		if (!"0".equals(count) || viewName.contains("@")) {
			// 解析后的表名重新赋值
			page.put("viewName", viewName);
			//获取自定义拼接条件
			String condition = (String) page.get("condition");
			if (!StringUtils.isEmpty(condition)) {
				condition = Base64Util.getFromBase64(condition);
				page.put("condition", " AND " + condition);
				if (CommonApiUtil.filterParam(page, "condition")) {
					page.remove("condition");
					throw new Exception("字段condition非法");
				}

			} else {
				//动态拼接sql文
				concatConditionStr(page);
			}
			//原始返回值
			List<Map<String, Object>> list = commonApiDao.getDataByViewName(page);
			//新返回值
			List<Map<String, Object>> resultList = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map :list) {
					Map<String, Object> resultMap = CommonApiUtil.transformUpperCase(map);
					resultList.add(resultMap);
				}
			}
			page.remove("condition");
			page.remove("selects");
			page.remove("key");
			page.setData(resultList);
			result.setData(page);
		} else {
			throw new Exception("请核对视图名/表名。viewName:" + page.get("viewName"));
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdateDataByTableNames(Map<String, Object> paramMap) throws Exception {
		//获取参数
		List<Map<String,Object>> paramList = (List<Map<String, Object>>) paramMap.get("paramList");
		for (int i = 0; i < paramList.size(); i++) {
			Map<String,Object> tableMap = paramList.get(i);
			String tableName = (String) tableMap.get("tableName");
			//校验表名是否存在
			checkTableName(tableName);
			//放入要操作的表
			paramMap.put("tableName",tableName);
			//动态拼接merge语句
			concatUpdateStr(tableName,paramMap,tableMap);
			//执行更新
			commonApiDao.addOrUpdateDataByTableNames(paramMap);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDataByTableNames(Map<String, Object> paramMap) throws Exception {
		//获取参数
		List<Map<String,Object>> paramList = (List<Map<String, Object>>) paramMap.get("deleteList");
		for (int i = 0; i < paramList.size(); i++) {
			Map<String,Object> tableMap = paramList.get(i);
			String tableName = (String) tableMap.get("tableName");
			String primaryKey = (String) tableMap.get("primaryKey");
			String flagCol = (String) tableMap.get("flagCol");
			String deleteType = (String) tableMap.get("deleteType");

			//校验表名
			checkTableName(tableName);
			paramMap.put("tableName", tableName);
			//校验主键字段
			checkColName(tableName, primaryKey);
			paramMap.put("primaryKey", primaryKey);
			//校验删除类型
			if(deleteType == null || deleteType.length() == 0) throw new Exception("删除类型(deleteType)必填 0-软删除 1-硬删除");
			paramMap.put("primaryKey", primaryKey);
			//校验删除的主键值,至少存在一个
			List<String> pkVals = (List<String>)tableMap.get("primaryKeyVal");
			if (pkVals == null || pkVals.size() == 0) throw new Exception("删除对象至少有一个(primaryKeyVal)");
			paramMap.put("primaryKeyVal", pkVals);
			if ("0".equals(deleteType)) {
				//检验软删除标志字段
				checkColName(tableName,flagCol);
				paramMap.put("flagCol", flagCol);
				//校验软删除标志字段值
				String flagColVal = (String) tableMap.get("flagColVal");
				if(flagColVal == null || flagColVal.length() == 0) throw new Exception("软删除标志字段值必填(flagColVal)");
				paramMap.put("flagColVal", flagColVal);
				//软删除
				commonApiDao.softDeleteDataByTableName(paramMap);
			} else {
				//硬删除
				commonApiDao.hardDeleteDataByTableName(paramMap);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdateOrDeleteDataByTableNames(Map<String, Object> paramMap) throws Exception {
		addOrUpdateDataByTableNames(paramMap);
		deleteDataByTableNames(paramMap);
	}

	@Override
	public Result executeExternalAPI(Map<String, Object> paramMap) throws Exception {
		Result result = new Result();
		Map<String, Object> externalUrlInfo = commonApiDao.getExternalUrlInfo(paramMap);
		if (externalUrlInfo == null ){
			throw new Exception("请核对参数。urlID:" + paramMap.get("urlID"));
		}
		String url = (String) externalUrlInfo.get("V_URL");
		String type = (String) externalUrlInfo.get("V_TYPE");
		String res = "";
		if ("post".equals(type)){
			String paramStr = JsonKit.toJson(paramMap.get("urlParam"));
			res = HttpKit.post(url,paramStr);
		} else {
			Map<String, Object> map = (Map<String, Object>) paramMap.get("urlParam");
			res = HttpKit.get(url + (map == null ? "" : CommonApiUtil.concatParamStr(map)));
		}
		Object obj = JSON.parseObject(res, Object.class);
		result.setData(obj);
		return result;
	}

	@Override
	public void insertRequestLogInfo(Map<String,Object> paramMap){
		commonApiDao.insertRequestLogInfo(paramMap);
	}

	/**
	 * 动态拼接sql文
	 * @param page
	 */
	private void concatConditionStr(Page page){
		StringBuffer sb = new StringBuffer();
		Set<String> keySet = page.keySet();
		for (String key : keySet) {
			//参数、值过滤
			if (CommonApiUtil.filterParam(page, key)) continue;

			String[] keys = key.split("%");
			//过滤无效参数
			Map<String, Object> colResult = commonApiDao.getColNameInfo((String) page.get("viewName"),keys[0]);
			// dblink访问表不进行字段校验
			if ((colResult == null || colResult.get("COLUMN_NAME") == null) && !((String) page.get("viewName")).contains("@")) continue;
			//拼接where条件
			CommonApiUtil.concatWhereStr(page, sb, key, keys);
		}
		//处理排序字段
		CommonApiUtil.concatOrderStr(page, sb);
	}

	/**
	 * 校验表名
	 * @param tableName
	 * @throws Exception
	 */
	private void checkTableName(String tableName) throws Exception {
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("表名不能为空(tableName)");
		}
		// 跨库操作免校验
		if(tableName.contains("@")) return;
		//校验表是否存在
		String count = commonApiDao.getTableNameInfo(tableName);
		if ("0".equals(count)) {
			throw new Exception("请核对表名。tableName:" + tableName);
		}
	}

	/**
	 * 更新语句动态拼接sql文
	 * @param tableName
	 * @param paramMap
	 * @param tableMap
	 */
	private void concatUpdateStr(String tableName, Map<String,Object> paramMap, Map<String,Object> tableMap) throws Exception {
		StringBuffer selectSB = new StringBuffer();
		StringBuffer onSB = new StringBuffer();
		StringBuffer setSB = new StringBuffer();
		StringBuffer insertSB = new StringBuffer();
		StringBuffer valuesSB = new StringBuffer();
		//修改字段
		Map<String,Object> cols = (Map<String, Object>) tableMap.get("cols");
		//条件字段
		String primaryKey = (String) tableMap.get("primaryKey");
		//检验字段
		checkColName(tableName, primaryKey);
		//拼接on条件
		onSB.append(" A." + primaryKey + " = " + "B." + primaryKey + " ");

		//获取序列参数(为空主键使用uuid,不为空使用序列)
		String seqName = (String) tableMap.get("seqName");

		Set<String> keySet = cols.keySet();
		int j = 0;
		for (String key : keySet) {
			//处理主键(新增时主键为空)
			if (primaryKey.equalsIgnoreCase(key) && ((String)cols.get(key)).length()==0){
				if (seqName == null || seqName.length() == 0) {
					//无序列,使用uuid
					cols.put(key, UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
				} else {
					//检查序列名称
					String count = commonApiDao.getSeqNameInfo(seqName);
					if ("0".equals(count)) {
						throw new Exception("序列名称不存在。seqName:" + seqName);
					}
					//获取序列值
					cols.put(key,commonApiDao.getNextSeqValue(seqName));
				}
			}
			//过滤非法参数,dblink免校验
			if ((!tableName.contains("@")) && filterInvalidParam(tableName, cols, key)) continue;

			//参数拼接
			//cols过滤掉ons中的字段
			if (!primaryKey.equalsIgnoreCase(key)){
				if (setSB.length() == 0){
					setSB.append(" A." + key + " = B." + key + " ");
				} else {
					setSB.append(" ,A." + key + " = B." + key + " ");
				}
			}
			if( j == 0){
				//cols中的全量字段
				selectSB.append(" '" + cols.get(key) + "' " + key + " ");
				//cols中的全量字段
				insertSB.append(" A." + key + " ");
				//cols中的全量字段
				valuesSB.append(" B." + key + " ");

			} else {
				selectSB.append(", '" + cols.get(key) + "' " + key + " ");
				insertSB.append(" ,A." + key + " ");
				valuesSB.append(" ,B." + key + " ");
			}
			j++;
		}
		if (selectSB.length() > 0) {
			paramMap.remove("selectStr");
			paramMap.remove("onStr");
			paramMap.remove("setStr");
			paramMap.remove("insertStr");
			paramMap.remove("valuesStr");
			paramMap.put("selectStr",selectSB.toString());
			paramMap.put("onStr",onSB.toString());
			paramMap.put("setStr",setSB.toString());
			paramMap.put("insertStr",insertSB.toString());
			paramMap.put("valuesStr",valuesSB.toString());
		}
	}
	/**
	 * 检验字段名
	 * @param tableName
	 * @param colName
	 * @throws Exception
	 */
	public void checkColName(String tableName, String colName) throws Exception {
		// 跨库操作免校验
		if(tableName.contains("@")) return;
		//过滤无效参数
		if (colName == null || colName.length() == 0 ) {
			throw new Exception("字段不能为空");
		}
		Map<String, Object> colResult = commonApiDao.getColNameInfo(tableName, colName);
		if (colResult == null || colResult.get("COLUMN_NAME") == null) {
			throw new Exception("请核对字段。" + colName);
		}
	}

	/**
	 * 过滤非法参数
	 * @param tableName
	 * @param cols
	 * @param key
	 * @return
	 */
	private boolean filterInvalidParam(String tableName, Map<String, Object> cols, String key) throws Exception {
		//过滤非条件参数
		if (CommonConstant.keyList.contains(key))
			return true;
		//防sql注入简单过滤
		boolean flag = false;
		for (String str : CommonConstant.sqlList) {
			if (( ((String) cols.get(key)).toUpperCase()).indexOf(str.toUpperCase()) != -1) {
				flag = true;
				break;
			}
		}
		if (flag) return true;
		//过滤无效参数
		Map<String, Object> colResult = commonApiDao.getColNameInfo(tableName, key);
		//1 字段是否存在
		if (colResult == null || colResult.get("COLUMN_NAME") == null) return true;
		//2 非空判断
		if ("N".equals(colResult.get("NULLABLE")) && (cols.get(key) == null || ((String)cols.get(key)).length() == 0)) {
			throw new Exception("数据库非空限制。" + key);
		}
		//3 长度判断
		String data_type = (String) colResult.get("DATA_TYPE");
		if ("VARCHAR2".equals(data_type) || "CHAR".equals(data_type)) {
			int strLen = ((BigDecimal) colResult.get("DATA_LENGTH")).intValue();
			if (strLen < ((String) cols.get(key)).length()) {
				throw new Exception("数据库字符类型长度限制(" + strLen + ")。" + key);
			}
		} else if ("NUMBER".equals(colResult.get("DATA_TYPE"))) {
			if (cols.get(key) != null && ((String)cols.get(key)).length() != 0){
				String keyValue = (String)cols.get(key);
				String[] keyValues = keyValue.split("\\.");
				//小数位数
				int doubleLen = ((BigDecimal) colResult.get("DATA_SCALE")).intValue();
				//整数位数
				int intLen = ((BigDecimal) colResult.get("DATA_PRECISION")).intValue() - doubleLen;
				if (intLen < keyValues[0].length()){
					throw new Exception("数据库数字类型整数位长度限制(" + intLen + ")。" + key + ":" + cols.get(key));
				}
				if (keyValue.indexOf(".") != -1){
					if (doubleLen < keyValues[1].length()){
						throw new Exception("数据库数字类型小数位长度限制("+ doubleLen +")。" + key + ":" + cols.get(key));
					}
				}
			}
		}
		return false;
	}
}
