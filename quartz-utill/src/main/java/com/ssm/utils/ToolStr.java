package com.ssm.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 字符串操作工具类
 *
 * @author FaceFeel
 * @Created 2018-03-05 16:34
 **/
public class ToolStr {


    /**
     * 获取随机串
     *
     * @return "
     */
    public static synchronized Long getRandom() {
        long time = System.currentTimeMillis();
        int random = (int)(Math.random()*10);
        return time+random;
    }

    /**
     * 判断传入对象是否为空
     *
     * @return “”
     */
    public static <T> boolean isEmpty(T obj) {
        //返回空则说明第一次大的校验存在空,反之不为空
        if (validateObj(obj)) return true;
        //第二次校验的是实体内部的属性值,如果全部为空则返回true,反之为false
        if (isAllFieldNull(obj)) return true;
        return false;
    }

    /**
     * 校验传入的数据是否为空
     *
     * @param obj 需要被校验的对象
     * @param <T> ""
     * @return ""
     */
    private static <T> boolean validateObj(T obj) {
        if (obj == null || obj == "") {
            return true;
        }
        if (obj instanceof String) {
            if (((String) obj).length() < 1 || ((String) obj).isEmpty()) return true;
        }
        if (obj instanceof List) {
            if (((List) obj).isEmpty() || ((List) obj).size() < 1) return true;
        }
        if (obj instanceof Map) {
            if (((Map) obj).isEmpty() || ((Map) obj).size() < 1) return true;
        }
        return false;
    }

    /**
     * 判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
     *
     * @param obj 被校验的对象
     * @return ""
     */
    private static boolean isAllFieldNull(Object obj) {
        // 得到类对象
        Class stuCla = obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        //遍历属性
        for (Field f : fs) {
            // 设置属性是可以访问的(私有的也可以)
            f.setAccessible(true);
            // 得到此属性的值
            Object val;
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return true;
            }
            //只要有1个属性不为空,那么就不是所有的属性值都为空
            if (val != null && !validateObj(val)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把字节数组转换成字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String byte2Str(byte[] bytes) {

        if (bytes.length == 0) {
            return "";
        }
        return new String(bytes);
    }

    /**
     * 把字符转换成字节数组
     *
     * @param str        需要转换的字符
     * @param encodeType 转换的编码类型
     * @return 字节数组
     */
    public static byte[] str2Byte(String str, String encodeType) {

        if (isBlank(str) || isBlank(encodeType)) {
            return new byte[0];
        }

        try {
            return str.getBytes(encodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * 把字符串转换成字节数组
     *
     * @param str 需要转换的字符
     * @return 字节数组
     */
    public static byte[] str2Byte(String str) {

        if (isBlank(str)) {
            return new byte[0];
        }

        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 不为空判断方法
     *
     * @param str 被判断字符串
     * @return 布尔
     */
    public static boolean notBlank(String str) {

        if (str == null || str.length() <= 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c) || Character.isDigit(c) || Character.isLetter(c)) {
                //只要存在数字/英文/中文就返回true
                return true;
            }
        }
        return false;
    }

    /**
     * 为空判断方法
     *
     * @param str 被判断字符串
     * @return 布尔
     */
    public static boolean isBlank(String str) {

        if (str == null || str.length() <= 0) {
            return true;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c) || Character.isDigit(c) || Character.isLetter(c)) {
                //只要存在数字/英文/中文就返回true
                return false;
            }
        }
        return true;
    }

    /**
     * 中文判断方法
     *
     * @param c 被判断字节码
     * @return 布尔
     */
    private static boolean isChinese(char c) {
        // 根据字节码判断
        return c >= 0x4E00 && c <= 0x9FA5;
    }
}
