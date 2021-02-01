package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Controller
@RequestMapping("admin/sys/common")
public class CommonController {
	
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);

	//处理图片上传
    @RequestMapping(value="/uploadImg", method = RequestMethod.POST)
    public @ResponseBody ResultBean uploadImg(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String filePath = FileUtils.getTmpPath();
        try {
        	uploadFile(file.getBytes(), filePath, fileName);
			return new ResultBean(0, null,fileName);
		}catch(Exception ex) {
			logger.error("上传图片出错：",ex);
			return new ResultBean(200, "上传图片出错!",null);
		}
    }
    
	//处理文件上传
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    public @ResponseBody ResultBean uploadFile(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String filePath = FileUtils.getTmpPath();
        try {
        	uploadFile(file.getBytes(), filePath, fileName);
			return new ResultBean(0, null,fileName);
		}catch(Exception ex) {
			logger.error("上传文件出错：",ex);
			return new ResultBean(200, "上传文件出错!",null);
		}
    }
    
    @RequestMapping("/tmpImg")
    public void tmpImg(HttpServletResponse response,String imageName) {
    	try {
        	File imageFile = new File(FileUtils.getTmpPath() + imageName);
            InputStream is=new FileInputStream(imageFile);
            BufferedImage bi=ImageIO.read(is);
            ImageIO.write(bi, "jpg", response.getOutputStream());
    	}catch(Exception ex) {
    		logger.error("临时图片访问出错：",ex);
    	}
    }
    
    private static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
        File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
