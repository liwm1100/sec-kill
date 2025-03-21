package com.liwm.sk.common.utils;


import java.util.List;

public class JsonBeanUtil {

    public static <T> T copyObject(Object source, Class<T> clazz) {
        String json = JsonUtil.toJson(source);
        return JsonUtil.toObject(json, clazz);
    }

    public static <T> List<T> copyList(Object source, Class<T> clazz) {
        String json = JsonUtil.toJson(source);
        return JsonUtil.toList(json, clazz);
    }

}
