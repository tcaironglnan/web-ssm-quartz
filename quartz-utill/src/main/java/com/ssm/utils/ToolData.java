package com.ssm.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 读取文本文件工具
 *
 * @author FaceFeel
 * @Created 2018-03-22 11:29
 **/
public class ToolData {


    public static void write() {

    }

    /**
     * @param fileContent
     * @return
     */
    public static boolean writeFile(String savePath, String fileContent) {

        if (Str.isBlank(savePath) || Str.isBlank(fileContent)) {
            return false;
        }

        File file = new File(savePath);
        FileWriter fileWriter = null;

        if (!file.isFile()) {
            return false;
        }

        try {
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    return false;
                }
            }

            fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {

        if (Str.isBlank(filePath)) {
            return null;
        }

        File file = new File(filePath);

        if (!file.isFile()){
            return null;
        }

        StringBuffer sb = new StringBuffer();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
            int len;

            while ((len = fileReader.read()) != -1) {
                sb.append((char) len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
