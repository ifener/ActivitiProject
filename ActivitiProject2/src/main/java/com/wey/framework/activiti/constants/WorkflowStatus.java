package com.wey.framework.activiti.constants;

/**
 * 工作流状态枚举
 * @author weisunqing
 *
 */
public enum WorkflowStatus {
	
	UNSUBMIT(0L,"未提交"),SUBMIT(10L,"已提交"),PROCESSING(20L,"审批中"),PASS(30L,"审批通过"),UNPASS(40L,"审批不通过");
	
	private Long code;
	private String name;
	
	WorkflowStatus(Long code,String name) {
		this.code = code;
		this.name= name;
	}

	public Long getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getWorkflowName(Long code) {
		if(code==null) {
			return null;
		}
		
		WorkflowStatus[] values = WorkflowStatus.values();
		for(WorkflowStatus ws:values) {
			if(ws.getCode()==code) {
				return ws.getName();
			}
		}
		return null;
	}
	
}
