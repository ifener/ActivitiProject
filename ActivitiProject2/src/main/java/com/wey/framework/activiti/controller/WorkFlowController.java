package com.wey.framework.activiti.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/workflow")
public class WorkFlowController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@GetMapping("/workflows")
	public String workflows(Model model,@RequestParam(name="pageIndex",defaultValue="1") Long pageIndex,@RequestParam(name="pageSize",defaultValue="10") Long pageSize) {
		
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().listPage((pageSize.intValue()-pageIndex.intValue())*pageSize.intValue(), pageSize.intValue());
		
		model.addAttribute("list", processDefinitions);
		
		return "workflow/workflow";
	}
	
	@PostMapping("/deploy")
	public String deploy(@RequestParam("file") MultipartFile file,@RequestParam("filename") String filename) {
		
		if(file!=null) {
			try {
				//Deployment deploy = repositoryService.createDeployment().addInputStream(filename, file.getInputStream()).deploy();
				ZipInputStream zis = new ZipInputStream(file.getInputStream());
				Deployment deploy = repositoryService.createDeployment().name(filename).addZipInputStream(zis).deploy();
				System.out.println(deploy.getId());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(file);
		return "redirect:workflows";
	}
	
	/**
	 * 
     *采用spring提供的上传文件的方法
     
    @RequestMapping("springUpload")
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
         long  startTime=System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String path="E:/springUpload"+file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
                 
            }
           
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
    return "/success"; 
    }
	 */
}
