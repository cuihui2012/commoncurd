package com.common.curd.commoncurd.utils;

import com.common.curd.commoncurd.constant.CommonConstant;
import com.common.curd.commoncurd.model.Page;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonApiUtil {

    /**
     * blob转byte
     * @param blob
     * @return
     */
    public static byte[] blobToBytes(Blob blob) {
        BufferedInputStream bufferedInputStream = null;
        try {
            //利用Blob自带的一个函数去将blob转换成InputStream
            bufferedInputStream = new BufferedInputStream(blob.getBinaryStream());
            //申请一个字节流，长度和blob相同
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;
            while (offset < len//确保不会读过头
                    && (read = bufferedInputStream.read(bytes, offset, len - offset)) >= 0) {
                //BufferedInputStream内部有一个缓冲区，默认大小为8M，每次调用read方法的时候，它首先尝试从缓冲区里读取数据，
                //若读取失败（缓冲区无可读数据），则选择从物理数据源（譬如文件）读取新数据（这里会尝试尽可能读取多的字节）放入到缓冲区中，
                //最后再将缓冲区中的内容部分或全部返回给用户
                //也就是说read函数一次性可能读不完，所以可能会分多次读，于是就有了上面的逻辑
                offset += read;
            }
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                return null;
            }
        }
    }

    /**
     * clob转String
     * @param clob
     * @return
     */
    public static String clobToString(Clob clob)
    {
        if(clob == null) {
            return null;
        }
        try
        {
            Reader inStreamDoc = clob.getCharacterStream();

            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException es)
        {
            es.printStackTrace();
        }
        return null;
    }

    /**
     * map key转小写,也可以使用反射动态生成bean,使用反射效率可能会低
     * @param orgMap
     * @return
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
            Object newValue = orgMap.get(key);
            if (newValue instanceof Blob){
                resultMap.put(newKey, Base64.encodeBase64String(blobToBytes((Blob) newValue)));
                continue;
            }
            if (newValue instanceof Clob){
                resultMap.put(newKey, clobToString((Clob) newValue));
                continue;
            }
            if (newValue instanceof Timestamp || newValue instanceof Date){
                SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeDtr = f.format(newValue).split("\\.")[0];
                resultMap.put(newKey, timeDtr);
                continue;
            }
            resultMap.put(newKey, newValue.toString());
        }
        return resultMap;
    }

    /**
     * list参数转换
     * @param params
     * @return
     */
    public static String exchangeParam(List<String> params) {
        if(params != null && params.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i = 0; i < params.size(); i++) {
                if (i == 0){
                    sb.append("'" + params.get(i) + "'");
                }else{
                    sb.append(",'" + params.get(i) + "'");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return null;
    }

    /**
     * sql防注入,value值简单校验
     * @param keyValue
     * @return
     */
    public static boolean checkKeyValue(String keyValue) {
        boolean flag = false;
        for (String str : CommonConstant.sqlList) {
            if ((keyValue.toUpperCase()).indexOf(str.toUpperCase()) != -1) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 参数、值过滤
     * @param page
     * @param key
     * @return
     */
    public static boolean filterParam(Page page, String key) {
        //过滤非条件参数&无值参数
        if (CommonConstant.keyList.contains(key) || page.get(key) == null || page.get(key).toString().replaceAll("\\[","").replaceAll("]","").length() == 0)
            return true;
        //防sql注入简单过滤
        if (checkKeyValue(page.get(key).toString())) return true;
        return false;
    }

    /**
     * 拼接where条件
     * @param page
     * @param sb
     * @param key
     * @param keys
     */
    public static void concatWhereStr(Page page, StringBuffer sb, String key, String[] keys) {
        if (keys.length != 1) {
            String type = keys[1];
            if ("list".equalsIgnoreCase(type)){
                String str = exchangeParam((List<String>)page.get(key));
                if (str != null) {
                    sb.append(" AND " + keys[0] + " in " + str);
                }
            } else if ("like".equalsIgnoreCase(type)){
                sb.append(" AND " + keys[0] + " like '%" + page.get(key) + "%' ");
            }  else if ("gt".equalsIgnoreCase(type)){
                sb.append(" AND " + keys[0] + " > " + page.get(key) + " ");
            }  else if ("lt".equalsIgnoreCase(type)){
                sb.append(" AND " + keys[0] + " < " + page.get(key) + " ");
            }  else if ("ge".equalsIgnoreCase(type)){
                sb.append(" AND " + keys[0] + " >= " + page.get(key) + " ");
            }  else if ("le".equalsIgnoreCase(type)){
                sb.append(" AND " + keys[0] + " <= " + page.get(key) + " ");
            } else {
                sb.append(" AND " + keys[0] + " = '" + page.get(key) + "' ");
            }
        } else {
            sb.append(" AND " + key + " = '" + page.get(key) + "' ");
        }
    }

    /**
     * 拼接排序字段
     * @param page
     * @param sb
     */
    public static void concatOrderStr(Page page, StringBuffer sb) {
        String orderCol = (String) page.get("orderCol");
        if (orderCol != null && orderCol.length() > 0 && !checkKeyValue(orderCol)){
            sb.append(" order by " + orderCol);
        }
        if (sb.length() > 0) {
            page.put("condition",sb.toString());
        }
    }

    /**
     * url动态拼接参数
     * @param paramMap
     */
    public static String concatParamStr(Map<String, Object> paramMap){
        StringBuffer sb = new StringBuffer();
        Set<String> keySet = paramMap.keySet();
        int i =0;
        for (String key : keySet) {
            if (i==0){
                sb.append("?" + key + "=" + paramMap.get(key));
            } else {
                sb.append("&" + key + "=" + paramMap.get(key));
            }
            i++;
        }
        return sb.toString();
    }
}
