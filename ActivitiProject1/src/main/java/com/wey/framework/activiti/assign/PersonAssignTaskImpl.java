package com.wey.framework.activiti.assign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * ʹ���������ָ����Ա
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
		
		delegateTask.setAssignee("�Ÿ���");

	}

}
