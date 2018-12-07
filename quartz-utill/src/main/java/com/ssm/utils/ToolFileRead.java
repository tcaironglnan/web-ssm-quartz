package com.ssm.utils;

import com.google.common.collect.Maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 读取文件相关的工具类
 *
 * @author FaceFeel
 * @Created 2018-03-12 17:23
 **/
public class ToolFileRead {


    /**
     * 读取properties文件,然后再把配置文件的内容转换成Map<String,String></>
     *
     * @param filePath 文件路径
     * @return Map<String   ,   String></>
     */
    public static Map<String, String> readProperties(String filePath) {

        if (Str.isBlank(filePath)) {
            return null;
        }

        Properties pro = new Properties();
        Map<String, String> proMap = Maps.newHashMap();
        File file = new File(filePath);

        try {
            FileInputStream fis = new FileInputStream(file);
            pro.load(fis);
            for (String key : pro.stringPropertyNames()) {
                proMap.put(key, pro.getProperty(key));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return proMap;
    }
}
