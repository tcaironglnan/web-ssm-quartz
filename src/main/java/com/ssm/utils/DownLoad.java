package com.ssm.utils;

import java.io.*;

/**
 * 文件下载类
 *
 * @author FaceFeel
 * @Created 2018-03-05 15:32
 **/
public class DownLoad {

    /**
     * 文件下载方法
     *
     * @param srcFilePath  需要下载的文件路径
     * @param saveFilePath 文件下载后需要保存的路径
     */
    public static void upload(String srcFilePath, String saveFilePath) {

        //创建需要复制的文件
        File filePath = new File(srcFilePath);
        //创建需要粘贴的文件
        File outPath = new File(saveFilePath + filePath.getName());
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            //创建文件输入流
            fis = new FileInputStream(filePath);
            //创建文件输入流缓冲区
            bis = new BufferedInputStream(fis);
            //创建文件输出流
            fos = new FileOutputStream(outPath);
            //创建文件输出流缓冲区
            bos = new BufferedOutputStream(fos);

            //定义字节数组
            byte[] buf = new byte[1024];
            //定义读写文件的长度
            int len;

            //遍历读写文件
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            try {
                if (bos != null){
                    bos.close();
                }
                if (fos != null){
                    fos.close();
                }
                if (bis != null){
                    bis.close();
                }
                if (fis != null){
                    fis.close();
                }
            }catch (Exception e){

            }
        }
    }
}
