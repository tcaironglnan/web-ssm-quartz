package com.ssm.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

/**
 * 公共服务类  用于处理一些公共部分的功能
 * Created by SeanDragon on 2016/5/18 0018.
 */
public class PublicService {

    private static Logger logger = Logger.getLogger(PublicService.class);

    private static String picPath;

    /**
     * 判断当前windows系统是否含有D盘,如果存在,则保存在D盘
     */
    static {
        boolean isWindowsOS = isWindowsOS();
    }


    /**
     * 获取验证码
     *
     * @param response response
     * @param session  会话
     */
    public static void getVaildCode(HttpServletResponse response, HttpSession session) {

        char[] a_z = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        int width = 17 * 4;
        int height = 30;
        int red, green, blue;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        Random rd = new Random();

        g.setColor(Color.WHITE);
        g.fillRect(1, 1, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(1, 1, width - 1, height - 1);

        g.setColor(Color.YELLOW);
        for (int i = 1; i <= 15; i++) {
            int x = rd.nextInt(i);
            int y = rd.nextInt(height);
            int x1 = rd.nextInt(width);
            int y1 = rd.nextInt(height);
            g.drawLine(x, y, x1, y1);
        }

        Font f = new Font("微软雅黑", Font.PLAIN, 28);
        g.setFont(f);


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = a_z[rd.nextInt(a_z.length)];
            sb.append(c);
            red = rd.nextInt(255);
            green = rd.nextInt(255);
            blue = rd.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(String.valueOf(c), 16 * i, 28);
        }

        session.setAttribute("vaildCode", sb.toString());

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (sos != null)
                try {
                    sos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
        }
    }

    /**
     * 生成随机字符串的方法
     *
     * @param size 设定产生值的位数
     * @return 字符串
     */
    public static String getVaildCode(int size) {
        Random rd = new Random();
        char[] a_z = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            char c = a_z[rd.nextInt(a_z.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 通过特殊的地址获取图片
     *
     * @param response response
     * @param path     图片地址(通常格式为*XXXX;*)
     */
    public static void getUploadPic(HttpServletResponse response, String path) {
        FileInputStream fis = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            File file = new File(path.equals("") ? "C:\\Users\\SeanDragon\\Pictures\\1.jpg" : path);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        } finally {
            if (fis != null) {
                try {
                    out.close();
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    /**
     * 把传入的参数存放到map中返回的map方法
     *
     * @param navTabId
     * @param message  需要存放的信息参数
     * @param status   状态值
     * @param close    是否关闭开关
     * @return Map
     */
    public static Map getReturnMap(String navTabId, String message, int status, boolean close) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("navTabId", navTabId);
        map.put("message", message + (status == 200 ? "成功" : "失败") + "了");
        map.put("statusCode", status);
        if (close) map.put("callbackType", "closeCurrent");
        return map;
    }

    /**
     * 对java bean 进行map操作
     *
     * @param object 对象
     * @return map
     */
    public static Map toMap(Object object) {
        Map map = new LinkedHashMap();

        Field[] e = object.getClass().getDeclaredFields();

        try {
            for (Field f : e) {
                f.setAccessible(true);
                Object value = f.get(object);
                if (value instanceof ArrayList) {
                    ArrayList blist = (ArrayList) value;
                    List list = new ArrayList();

                    for (Object aBlist : blist) {
                        list.add(toMap(aBlist));
                    }

                    map.put(f.getName(), list);
                } else {
                    map.put(f.getName(), value);
                }

                f.setAccessible(false);
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 把map转换成为实体类的方法
     *
     * @param map 要转换的map
     * @param t   实体类参数
     * @param <T> 实体类泛型
     * @return 转换完成的实体类
     */
    public static <T> T toObject(Map map, T t) {
        return (T) ToolJson.jsonToModel(ToolJson.modelToJson(map), t.getClass());
    }

    /**
     * 把map转换成为实体类的方法
     *
     * @param map   要转换的map
     * @param clazz 实体类参数
     * @param <T>   实体类泛型
     * @return 转换完成的实体类
     */
    public static <T> T toObject(Map map, Class clazz) {
        return (T) ToolJson.jsonToModel(ToolJson.modelToJson(map), clazz);
    }

    /**
     * 是否是windows
     * 判断是否是windows系统的方法
     *
     * @return
     */
    protected static boolean isWindowsOS() {
        return System.getProperties().get("os.name").toString().toLowerCase().contains("windows");
    }

    /**
     * 判断是否存在D盘方法
     * 是不是有d盘
     *
     * @return
     */
    public static boolean haveD() {
        File[] fileList = File.listRoots();
        for (File file : fileList) {
            if (file.isDirectory() && file.getAbsolutePath().toLowerCase().contains("d")) return true;
        }
        return false;
    }

}
