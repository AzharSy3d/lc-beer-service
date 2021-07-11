package com.azsyed.lcbeerservice.util;

public class DataUtil {

    public static Boolean nullSafe(Boolean b){
        return b==null?false:b;
    }

    public static boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }

}
