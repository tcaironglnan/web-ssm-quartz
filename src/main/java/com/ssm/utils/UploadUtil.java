package com.ssm.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 上传图片工具类
 *
 * @author FaceFeel
 * @date 2017/2/21
 */

public class UploadUtil {

    public static String upload(MultipartFile file, String pic_path) throws Exception {

        //原始名称
        String originalFilename = file.getOriginalFilename();
        //上传图片
        if (file != null && originalFilename != null && originalFilename.length() > 0) {
            //新的图片名称
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新图片
            File newFile = new File(pic_path + newFileName);
            //将内存中的数据写入磁盘
            file.transferTo(newFile);
            return pic_path + newFileName;
        }
        return "";
    }
}
