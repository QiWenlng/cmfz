package com.hugo.common.utils;

import java.util.UUID;

/**
 * 用来生成项目中主键的工具类
 */
public class UUIDUtils {


    /**
     * 生成主键方法
     *
     * @return 返回生成uuid主键
     */
    public static String getId() {

        return UUID.randomUUID().toString();
    }
}
