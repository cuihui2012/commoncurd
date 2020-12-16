package com.common.curd.commoncurd.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    // 加密
    public static String getBase64(String str) {
        String encodeBase64String = Base64.encodeBase64String(str.getBytes());
        return encodeBase64String;
    }

    // 解密
    public static String getFromBase64(String s) {
        byte[] decodeBase64 = Base64.decodeBase64(s);
        s=new String(decodeBase64);
        return s;
    }

    public static void main(String[] args) {
        String a ="viewName=TAPP_JSCDP_ZC_DWZCSYFXTJ&condition=AND sjrq = (SELECT MAX(sjrq) FROM TAPP_JSCDP_ZC_DWZCSYFXTJ)";
        String base64 = getBase64(a);
        System.out.println(base64);
        String fromBase64 = getFromBase64(base64);
        System.out.println(fromBase64);
    }
}
