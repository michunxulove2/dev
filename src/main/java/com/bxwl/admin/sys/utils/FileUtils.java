package com.bxwl.admin.sys.utils;

import java.io.File;

public class FileUtils {

	//项目根目录
	private static String rootPath;
	
	static {
		rootPath = FileUtil.getDirFromClassLoader(FileUtils.class);
	}

	/**
	 * 获取文件临时目录
	 * @return
	 */
	public static String getTmpPath() {
		return rootPath + "/tmp/";
	}
	
	/**
	 * 根据文件名获取临时文件
	 * @param fileName
	 * @return
	 */
	public static File getFileByName(String fileName) {
		File tmpFile = new File(getTmpPath() + fileName);
		if(tmpFile.exists()) {
			return tmpFile;
		}
		return null;
	}

}
