package com.wey.framework.bo;

import java.io.Serializable;

import com.wey.framework.model.BaseObject;

public class BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseObject baseVO = null;

	private Long[] ids = null;

	public BaseObject getBaseVO() {
		return baseVO;
	}

	public void setBaseVO(BaseObject baseVO) {
		this.baseVO = baseVO;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	
}
