package com.ssm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

/**
 * json作为中间件转换工具类
 *
 * @author FaceFeel
 * @Created 2018-03-02 10:14
 **/
public class ToolJson {

    private static final Gson gson = new GsonBuilder().create();


    public static <T> T jsonToModel(String json, Class<T> target) {

        return gson.fromJson(json, target);
    }

    public static <T> T jsonToList(String json, Class<T> target) {

        return gson.fromJson(json, target);
    }

    public static <T> T jsonToMap(String json, Class<T> target) {

        return gson.fromJson(json, target);
    }

    public static <T> String anyToJson(T target) {

        return gson.toJson(target);
    }

    public static String listToJson(List list) {

        return gson.toJson(list);
    }

    public static String mapToJson(Map map) {

        return gson.toJson(map);
    }

    public static <T> String modelToJson(T target) {

        return gson.toJson(target);
    }
}
