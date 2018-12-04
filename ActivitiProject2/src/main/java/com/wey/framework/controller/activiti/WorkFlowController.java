package com.wey.framework.controller.activiti;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wey.framework.activiti.bo.CommentBO;
import com.wey.framework.activiti.bo.ProcessDefinitionBO;
import com.wey.framework.activiti.service.WorkflowManager;
import com.wey.framework.controller.BaseController;
import com.wey.framework.util.Pagination;

@Controller
@RequestMapping("/workflow")
public class WorkFlowController extends BaseController {

	@Autowired
	private WorkflowManager workflowManager;

	@RequestMapping("/list")
	public String list(Pagination page, ProcessDefinitionBO processDefinitionBO, Model model) {
		if (page == null) {
			page = new Pagination();
		}

		page.setSearch(processDefinitionBO);
		workflowManager.findProcessDefinition(page);
		model.addAttribute("page", page);
		model.addAttribute("processDefinitionBO", processDefinitionBO);
		return custom();
	}

	@GetMapping("/add")
	public String add() {
		return "workflow/form";
	}

	@PostMapping("/deploy")
	public String deploy(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
		if (file != null) {
			try {
				ZipInputStream zis = new ZipInputStream(file.getInputStream());
				workflowManager.deploy(zis, name);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(file);
		return "redirect:list";
	}

	@GetMapping("/viewImage")
	public String viewImage(@RequestParam("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName, HttpServletResponse response) {
		InputStream inputStream = workflowManager.findProcessDefinitionImage(deploymentId, resourceName);
		if (inputStream != null) {
			ServletOutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
				byte[] bytes = new byte[1024];
				while (inputStream.read(bytes) > 0) {
					outputStream.write(bytes);
				}

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

	@GetMapping("/viewImageBase64")
	public String viewImageBase64(@RequestParam("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName, Model model) {
		InputStream inputStream = workflowManager.findProcessDefinitionImage(deploymentId, resourceName);
		if (inputStream != null) {
			ByteArrayOutputStream outputStream = null;
			try {
				outputStream = new ByteArrayOutputStream();
				byte[] bytes = new byte[1024];
				while (inputStream.read(bytes) > 0) {
					outputStream.write(bytes);
				}
				byte[] byteArray = outputStream.toByteArray();
				String png = new String(Base64.encodeBase64(byteArray));
				model.addAttribute("png", png);
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
		return custom();
	}

	@PostMapping("/remove")
	public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId) {
		workflowManager.deleteProcessDefinition(deploymentId);
		return "redirect:workflows";
	}

	@GetMapping("/hiscomments")
	public String hiscomments(String processKey, Long bizId, Model model) {
		List<CommentBO> comments = workflowManager.findHistoricalComments(processKey, bizId);
		model.addAttribute("comments", comments);
		return custom();
	}

	@GetMapping("/currentProcessView")
	public String currentProcessView(String processKey, Long bizId, Model model) {
		// 获取流程定义
		ProcessDefinition processDefinition = workflowManager.findProcessDefinitionByBizId(processKey, bizId);

		// 获取坐标
		Map<String, Object> coordinate = workflowManager.findCurrentTaskCoordinate(processKey, bizId);

		model.addAttribute("deploymentId", processDefinition.getDeploymentId());
		// 流程图片
		model.addAttribute("resourceName", processDefinition.getDiagramResourceName());
		model.addAttribute("local", coordinate);

		return custom();
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
