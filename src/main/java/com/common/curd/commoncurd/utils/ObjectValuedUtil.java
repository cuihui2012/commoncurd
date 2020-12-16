package com.common.curd.commoncurd.utils;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * company xxx
 *
 * @author cuihui
 * @date 2019年7月13日下午12:33:14
 * @Desc 对象赋值工具
 */
public class ObjectValuedUtil {

    private static Map<String, Map<String, Field>> exist = new ConcurrentHashMap<String, Map<String, Field>>();

    @SuppressWarnings("unchecked")
    public static void setObjectValue(Object object, Map<String, Object> paramsMap) {
        Map<String, Field> fieldMap = null;
        synchronized (exist) {
            exist.get(object.getClass().toString());
            if (fieldMap == null) {
                fieldMap = new HashMap<>(10);
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    fieldMap.put(field.getName(), field);
                }
                exist.put(object.getClass().toString(), fieldMap);
            }
        }
        Set<String> keys = paramsMap.keySet();
        Map<String, Object> oMap = (object instanceof Map) ? (Map<String, Object>) object : null;
        for (String key : keys) {
            Field field = fieldMap.get(key);
            Object value = paramsMap.get(key);
            if (field == null) {
                if (oMap != null) {
                    oMap.put(key, value);
                }
                continue;
            }
            try {
                Class<?> cls = object.getClass();
                if (value == null || value.equals("")) {
                    field.set(object, null);
                } else {
                    if (value instanceof String) {
                        Method fieldSetMet = cls.getMethod(parSetName(field.getName()),
                                field.getType());
                        try {
                            fieldSetMet.invoke(object, parseByType(field, value.toString()));
                        } catch (Exception e) {
                            field.set(object, parseByType(field, value.toString()));
                        }
                    } else if (value instanceof List) {
                        throw new RuntimeException("不支持的转换类型:" + field.getType() + ",如需扩展,请联系chender");
                    } else {
                        throw new RuntimeException("待扩展数据类型:" + value.getClass());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("通过反射给对象赋值出现异常", e);
            }
        }


    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    public static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }

        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 从request中获取参数，赋值到传入的对象中
     *
     * @param object  需要赋值的对象
     * @param request http请求
     */
    @SuppressWarnings("unchecked")
    public static void setObjectValue(Object object, HttpServletRequest request) {
        Enumeration<String> em = request.getParameterNames();
        Map<String, Object> values = new HashMap<>(10);
        while (em.hasMoreElements()) {
            String name = em.nextElement();
            values.put(name, request.getParameter(name));
        }

        // 公共接口权限改造,放入自定义参数(查询项)
        String selects = (String) request.getAttribute("selects");
        if (!StringUtils.isEmpty(selects)) values.put("selects", selects);

        // 取拦截器中放入本地线程的request-body参数
        Map<String, Object> postParams = RequestThreadLocal.getPostRequestParams();
        if (postParams == null) {
            postParams = new HashMap<>();
        }
        values.putAll(postParams);
        setObjectValue(object, values);
    }

    private static Object parseByType(Field field, String sourceValue) {
        Object newValue = null;
        if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? "0" : sourceValue;
            newValue = Integer.valueOf(sourceValue);
        } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? "0" : sourceValue;
            newValue = Long.valueOf(sourceValue);
        } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? "0" : sourceValue;
            newValue = Double.valueOf(sourceValue);
        } else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? "0" : sourceValue;
            newValue = Float.valueOf(sourceValue);
        } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? "false"
                    : sourceValue;
            newValue = Boolean.valueOf(sourceValue);
        } else if (field.getType().equals(String.class)) {
            sourceValue = (sourceValue == null || sourceValue.equals("")) ? null : sourceValue;
            newValue = sourceValue;
        } else if (field.getType().equals(BigDecimal.class)) {
            newValue = (sourceValue == null || sourceValue.equals("")) ? null : new BigDecimal(sourceValue);
        } else if (field.getType().equals(Date.class)) {
            try {
                newValue = (sourceValue == null || sourceValue.equals("")) ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sourceValue);
            } catch (ParseException e) {
                throw new RuntimeException("传入的时间参数格式不正确");
            }
        } else {
            throw new RuntimeException("不支持的转换类型:" + field.getType() + ",如需扩展,请联系chender");
        }
        return newValue;
    }
}
