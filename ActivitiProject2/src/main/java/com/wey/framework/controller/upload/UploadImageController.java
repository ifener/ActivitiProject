package com.wey.framework.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.wey.framework.util.DateUtil;

@RequestMapping("/upload")
@Controller
public class UploadImageController {

	@RequestMapping(value = "/uploadImages", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public List<String> uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		List<String> imagePaths = new ArrayList<String>();
		try {
			
			String folder = "/upload/images/" + DateUtil.getDateTime("YYYYMMdd", new Date());
			String filePath = contextPath+folder;
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if (!file.isEmpty()) {
						String fileName = file.getOriginalFilename();
						String suffix = fileName.substring(fileName.lastIndexOf("."));
						fileName = "/" + System.currentTimeMillis() + suffix;;
						String savePath = filePath + fileName;
						File dest = new File(savePath);
						file.transferTo(dest);
						imagePaths.add(folder+fileName);
					}
				}
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imagePaths;
	}
}
