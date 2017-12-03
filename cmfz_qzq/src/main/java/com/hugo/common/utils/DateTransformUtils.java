package com.hugo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTransformUtils {
    //规范日期格式
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //字符串转为Util类型日期
    public static java.util.Date strToUtil(String str) {
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Util类型日期转换为Sql类型日期
    public static java.sql.Date utilToSql(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    //Util类型日期转换为字符串
    public static String utilToStr(java.util.Date utilDate) {
        return sdf.format(utilDate);
    }
}
