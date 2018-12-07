package com.ssm.utils;
import java.io.UnsupportedEncodingException;

/**
 * 字符串操作工具类
 *
 * @author FaceFeel
 * @Created 2018-03-05 16:34
 **/
public class Str {


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

        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
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
