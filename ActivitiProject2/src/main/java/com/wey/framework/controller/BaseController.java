package com.wey.framework.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.wey.framework.util.DateUtil;

public class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	private final String UPLOAD_IMAGE_ROOT_FOLDER = "/upload/images/";

	@Autowired
	protected HttpSession session;

	protected String saveUploadFile(MultipartFile file, String directoryRoot)
			throws IllegalStateException, IOException {

		String folder = UPLOAD_IMAGE_ROOT_FOLDER + DateUtil.getDateTime("YYYYMMdd", new Date());
		String filePath = directoryRoot + folder;
		File directory = new File(filePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			fileName = "/" + System.currentTimeMillis() + suffix;
			String savePath = filePath + fileName;

			logger.info("上传文件路径：" + savePath);

			File dest = new File(savePath);
			file.transferTo(dest);
			return folder + fileName;
		}
		return null;
	}

	protected String custom() {
		return getAnnotatedUrl();
	}

	private String getAnnotatedUrl() {
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		Method[] methods = this.getClass().getMethods();

		StringBuilder builder = new StringBuilder();
		RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			String[] values = requestMapping.value();
			if (values != null && values.length > 0) {
				String url = requestMapping.value()[0];
				if (StringUtils.hasText(url)) {
					logger.info("class requestMapping url is " + url);
					builder.append(url.substring(1));
				}
			}
		}

		/*
		 * for (Method m : methods) { if (m.getName().equals(methodName)) {
		 * requestMapping = m.getAnnotation(RequestMapping.class);
		 * logger.info("method requestMapping url is " + requestMapping.value()[0]);
		 * String value = requestMapping.value()[0]; if (value.equals("/add") ||
		 * value.equals("/load")) { builder.append("/form"); } else {
		 * builder.append(value); } break; } }
		 */

		if (methodName.equals("add") || methodName.equals("load")) {
			builder.append("/form");
		} else {
			builder.append("/" + methodName);
		}

		return builder.toString();
	}
}
