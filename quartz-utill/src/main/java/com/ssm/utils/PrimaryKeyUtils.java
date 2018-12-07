package com.ssm.utils;

/**
 *主键生成类
 *
 * @author FaceFeel
 * @date 17/3/9
 */
public class PrimaryKeyUtils {


    /**
     *对纳秒随机数取整方法
     * @return
     */
    private static long getRightNanoTime() {
        long nanoTime = System.nanoTime();
        if (nanoTime < 0) {
            return getRightNanoTime();
        } else {
            return nanoTime;
        }
    }

    /**
     * shift :
     * 5   32进制
     * 6   64进制
     * 放入long类型数字
     * @param i     数字
     * @param shift 2的几次幂
     * @return 经过转换的
     */
    public static String toUnsignedString(long i, int shift) {
        final char[] self = shift > 5 ? digits_$ : digits;
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = self[(int) (i & mask)];
            i >>>= shift;
        } while (i != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 32位
     */
    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
    /**
     * 64位
     */
    private final static char[] digits_$ = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '*', '$'
    };
}
