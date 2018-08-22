package com.facetuis.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileUtil {
    public static void mkdirs(String dir){
        if(StringUtils.isEmpty(dir)){
            return;
        }

        File file = new File(dir);
        if(file.isDirectory()){
            return;
        } else {
            file.mkdirs();
        }
    }
}
