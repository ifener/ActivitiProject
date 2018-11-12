package com.wey.framework.exception;

public class ServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String key){
		super(key);
	}
	
	public ServiceException(String key,Object... values){
		super(key,values);
	}
	
	public ServiceException(Throwable cause, String key, Object... values)
	  {
	    super(cause,  key, values);
	  }
}


