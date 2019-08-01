package com.zypcy.framework.fast.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件路径帮助类
 * zhuyu
 * 2019-08-01
 */
public class FilePathUtil {

    //获取项目根目录
    private static String filePath = FileUtil.getAbsolutePath("classpath:");

    /**
     * 获取项目所在目录
     * @return
     */
    public static String getRootPath(){
        int len = filePath.indexOf("fast");
        String path = filePath;
        if (len > 0) {
            path = filePath.substring(0, len) + "fast/";
        }
        return path;
    }

    /**
     * 获取文件上传存储路径，默认存储到项目所在目录 + /fast/upload/year/month/day/文件名
     * @return
     */
    public static String getFileUploadPath() {
        String path = getRootPath() + "upload/";
        //文件路径按年月日存放
        path = path + DateUtil.format(new Date(), "yyyy/MM/dd/");
        if(!FileUtil.exist(path)){
            FileUtil.mkdir(path);
        }
        return path;
    }

    /**
     * 获取word导出路径，默认存储到项目所在目录 + /fast/office/year/month/文件名
     * @return
     */
    public static String getOfficeExportPath(){
        String path = getRootPath() + "office/";
        path = path + DateUtil.format(new Date(), "yyyy/MM/");
        if(!FileUtil.exist(path)){
            FileUtil.mkdir(path);
        }
        return path;
    }

    /**
     * 获取资源文件绝对路径
     * @param resourceFilePath  "static/images/index_bg2.jpg"
     * @return
     */
    public static String getResourceFilePath(String resourceFilePath){
        String path = "";
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + resourceFilePath);
            path = file.getAbsolutePath();
        }catch (IOException ex){

        }
        return path;
    }
}
