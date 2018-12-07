package com.ssm.utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 关于验证码的工具类
 *
 * @author FaceFeel
 * @Created 2018-04-27 11:21
 */
public final class CaptchaUtil {

    private CaptchaUtil() {
    }

    /**
     * 随机字符字典
     */
    private static final char[] CHARS = {'2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成随机数
     */
    private static Random random = new Random();

    /**
     * 获取6位随机数
     * @return 随机码
     */
    private static String getRandomString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    /**
     * 获取随机数颜色
     * @return 颜色类
     */
    private static Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255),
                random.nextInt(255));
    }

    /**
     * 返回某颜色的反色
     * @param c 颜色类
     * @return 颜色类
     */
    private static Color getReverseColor(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(),
                255 - c.getBlue());
    }

    /**
     * 转换图片格式,并写入流中
     * @param request 请求
     * @param response 响应
     * @throws IOException 异常
     */
    public static void outputCaptcha(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("image/jpeg");

        String randomString = getRandomString();
        request.getSession(true).setAttribute("randomString", randomString);

        int width = 100;
        int height = 30;

        Color color = getRandomColor();
        Color reverse = getReverseColor(color);

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.setColor(reverse);
        g.drawString(randomString, 18, 20);
        for (int i = 0, n = random.nextInt(100); i < n; i++) {
            g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }

        // 转成JPEG格式
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpeg", out);
        out.flush();
    }
}