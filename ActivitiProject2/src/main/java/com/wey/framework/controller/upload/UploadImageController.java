package com.wey.framework.controller.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wey.framework.controller.BaseController;

@RequestMapping("/upload")
@Controller
public class UploadImageController extends BaseController {

	@RequestMapping(value = "/uploadImages", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public List<String> uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files,
			HttpServletRequest request) {
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		List<String> imagePaths = new ArrayList<String>();
		try {
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if (file != null) {
						String filePath = saveUploadFile(file, contextPath);
						if (StringUtils.hasText(filePath)) {
							imagePaths.add(filePath);
						}
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
