package com.ssm.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @author FaceFeel
 * @Created 2018-03-05 15:04
 **/
public class Zip {

    //region zip

    /**
     * 压缩文件方法
     *
     * @param srcFilePath  需要被压缩的文件路径
     * @param destFilePath 压缩完成后的保存路径
     */
    public static void zip(String srcFilePath, String destFilePath) {

        //获取需要压缩的文件
        File scr = new File(srcFilePath);
        //判断文件是否存在
        if (!scr.exists()) {
            throw new RuntimeException(srcFilePath + "have not");
        }

        //定义压缩后的保存路径
        File dest = new File(destFilePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;

        try {
            //定义文件输出流
            fos = new FileOutputStream(dest);
            //定义文件输出流缓冲区
            bos = new BufferedOutputStream(fos);
            //使用CRC32算法来校验数据的完整性
            cos = new CheckedOutputStream(bos, new CRC32());
            //定义压缩文件的输出流
            zos = new ZipOutputStream(cos);
            //定义文输出件路径
            String baseDir = "";

            //判断是否是文件
            if (scr.isFile()) {
                zipFile(zos, scr, baseDir);
            }
            //判断是否是文件夹
            if (scr.isDirectory()) {
                zipDirec(zos, scr, baseDir);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                //关闭流
                if (zos != null) {
                    zos.closeEntry();
                }
                if (zos != null) {
                    zos.close();
                }
                if (cos != null) {
                    cos.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 压缩文件方法
     *
     * @param zos     压缩文件输出流
     * @param src     需要被压缩的文件
     * @param baseDir 输出路径
     */
    private static void zipFile(ZipOutputStream zos, File src, String baseDir) {

        //判断文件是否存在
        if (!src.exists()) {
            return;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            //创建文件输入流
            fis = new FileInputStream(src);
            //创建文件输入流的缓冲区
            bis = new BufferedInputStream(fis);
            //按照文件路径创建ZipEntry
            ZipEntry zipEntry = new ZipEntry(baseDir + src.getName());
            //把ZipEntry放入到Zip输出流里
            zos.putNextEntry(zipEntry);

            //定义输出的字节数组
            byte[] buf = new byte[1024];
            //定义输出的字节长度
            int len;

            //遍历读写文件
            while ((len = bis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流,按照先打开后关闭的原则
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 处理多文件压缩方法
     *
     * @param zos     压缩文件输出流
     * @param src     需要被压缩的文件
     * @param baseDir 文件输出路径
     */
    private static void zipDirec(ZipOutputStream zos, File src, String baseDir) {

        //判断文件是否存在
        if (!src.exists()) {
            return;
        }

        //获取整个数据(包含多条的数据)
        File[] files = src.listFiles();

        //如果得到的数据为空,则直接返回空
        if (files == null) {
            return;
        }

        //判断文件长度是否为零
        if (files.length == 0) {
            try {
                //创建ZipEntry
                ZipEntry zipEntry = new ZipEntry(baseDir + src.getName() + File.separator);
                //把ZipEntry放入Zip输出流中
                zos.putNextEntry(zipEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //遍历读写文件
        for (File file : files) {
            zipFile(zos, file, baseDir + src.getName() + File.separator);
        }
    }
    //endregion

    //region unzip

    /**
     * 解压方法
     *
     * @param filePath  需要解压的文件
     * @param unZipPath 解压后存放的路径
     */
    public static void unzip(String filePath, String unZipPath) {

        //读取需要解压的文件
        File srcPath = new File(filePath);

        //判断需要解压的文件是否存在
        if (!srcPath.exists()) {
            throw new RuntimeException(filePath + "have not");
        }

        InputStream fis = null;
        FileOutputStream fos = null;

        try {
            //读取解压文件
            ZipFile zipFile = new ZipFile(srcPath);
            //获取解压文件的所有内容
            Enumeration entries = zipFile.entries();
            //创建ZipEntry
            ZipEntry zipEntry;

            //循环读取压缩包里的内容
            while (entries.hasMoreElements()) {
                //挨个获取ZipEntry,每个ZipEntry属于一个文件(压缩包内的文件)
                zipEntry = (ZipEntry) entries.nextElement();
                System.err.println("解压:" + zipEntry.getName());

                //判断获取的数据是否的文件夹
                if (zipEntry.isDirectory()) {
                    //定义文件夹路径
                    String dirPath = unZipPath + File.separator + zipEntry.getName();
                    File dir = new File(dirPath);
                    //按照文件夹路径创建文件夹
                    dir.mkdirs();
                } else {
                    //按照文件夹路径获取数据
                    File file = new File(unZipPath + File.separator + zipEntry.getName());
                    //判断数据是否存在
                    if (!file.exists()) {
                        //获取父路径
                        String dirs = file.getParent();
                        File parentDir = new File(dirs);
                        //按照获取到的父路径创建文件夹
                        parentDir.mkdirs();
                    }
                    //创建新的文件
                    file.createNewFile();
                    //开始读取文件
                    fis = zipFile.getInputStream(zipEntry);
                    //初始化输出流
                    fos = new FileOutputStream(file);

                    //定义读取文件的长度
                    int len;
                    //定义读取的字节数组
                    byte[] buf = new byte[1024];

                    //遍历读写文件
                    while ((len = fis.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {

                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
    }
    //endregion
}
