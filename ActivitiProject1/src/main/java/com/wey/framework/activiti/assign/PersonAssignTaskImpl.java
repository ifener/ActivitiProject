package com.wey.framework.activiti.assign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 使用类给流程指派人员
 * @author weisunqing
 *
 */
public class PersonAssignTaskImpl implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		delegateTask.setAssignee("张高丽");

	}

}
