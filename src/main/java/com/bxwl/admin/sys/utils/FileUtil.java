package com.bxwl.admin.sys.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public FileUtil() {
    }

    public static String bytes2Str(Long l) {
        if (l != null) {
            double d = (double)l / 1.073741824E9D;
            if (d >= 1.0D) {
                return (new BigDecimal(d)).setScale(2, 4).floatValue() + "GB";
            } else {
                d = (double)l / 1048576.0D;
                if (d >= 1.0D) {
                    return (new BigDecimal(d)).setScale(2, 4).floatValue() + "MB";
                } else {
                    d = (double)l / 1024.0D;
                    return d >= 1.0D ? (new BigDecimal(d)).setScale(2, 4).floatValue() + "KB" : l + "B";
                }
            }
        } else {
            return "0B";
        }
    }

    public static String getDirFromClassLoader(Class clazz) {
        try {
            String path = clazz.getName().replace(".", "/");
            path = "/" + path + ".class";
            URL url = clazz.getResource(path);
            String jarUrl = url.getPath();
            if (jarUrl.startsWith("file:")) {
                if (jarUrl.length() > 5) {
                    jarUrl = jarUrl.substring(5);
                }

                jarUrl = jarUrl.split("!")[0];
            } else {
                jarUrl = clazz.getResource("/").toString().substring(5);
            }

            File file = new File(jarUrl);
            return file.getParent();
        } catch (Exception var5) {
            logger.error("FileUtil:getDirFromClassLoader获取路径失败", var5);
            return null;
        }
    }
}
