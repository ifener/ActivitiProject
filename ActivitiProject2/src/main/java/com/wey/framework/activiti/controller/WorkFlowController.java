package com.wey.framework.activiti.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wey.framework.activiti.bo.ProcessDefinitionBO;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.util.Pagination;

@Controller
@RequestMapping("/workflow")
public class WorkFlowController {

	@Autowired
	private WorkflowManager workflowManager;

	@RequestMapping("/workflows")
	public String workflows(Pagination page,ProcessDefinitionBO processDefinitionBO,Model model) {
        if(page==null) {
        	page = new Pagination();
        }
        
        page.setSearch(processDefinitionBO);
		workflowManager.findProcessDefinition(page);
		model.addAttribute("page", page);
		model.addAttribute("processDefinitionBO", processDefinitionBO);
		return "workflow/workflow";
	}

	@PostMapping("/deploy")
	public String deploy(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename) {
		if (file != null) {
			try {
				ZipInputStream zis = new ZipInputStream(file.getInputStream());
				workflowManager.deploy(zis, filename);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(file);
		return "redirect:workflows";
	}
	
	@GetMapping("/viewImage")
	public String viewImage(@RequestParam("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName,HttpServletResponse response) {
		InputStream inputStream = workflowManager.findProcessDefinitionImage(deploymentId, resourceName);
		if(inputStream!=null) {
			ServletOutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
				byte[] bytes = new byte[1024];
				while(inputStream.read(bytes)>0) {
					outputStream.write(bytes);
				}
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@PostMapping("/remove")
	public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId) {
		workflowManager.deleteProcessDefinition(deploymentId);
		return "redirect:workflows";
	}

	/**
	 * 
	 * 采用spring提供的上传文件的方法
	 * 
	 * @RequestMapping("springUpload") public String springUpload(HttpServletRequest
	 * request) throws IllegalStateException, IOException { long
	 * startTime=System.currentTimeMillis(); //将当前上下文初始化给 CommonsMutipartResolver
	 * （多部分解析器） CommonsMultipartResolver multipartResolver=new
	 * CommonsMultipartResolver( request.getSession().getServletContext());
	 * //检查form中是否有enctype="multipart/form-data"
	 * if(multipartResolver.isMultipart(request)) { //将request变成多部分request
	 * MultipartHttpServletRequest
	 * multiRequest=(MultipartHttpServletRequest)request; //获取multiRequest 中所有的文件名
	 * Iterator iter=multiRequest.getFileNames();
	 * 
	 * while(iter.hasNext()) { //一次遍历所有文件 MultipartFile
	 * file=multiRequest.getFile(iter.next().toString()); if(file!=null) { String
	 * path="E:/springUpload"+file.getOriginalFilename(); //上传 file.transferTo(new
	 * File(path)); }
	 * 
	 * }
	 * 
	 * } long endTime=System.currentTimeMillis();
	 * System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
	 * return "/success"; }
	 */
}
