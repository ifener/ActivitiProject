package com.wey.framework.exception;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private Object[] values;
	
	public BaseException(){}
	
	public BaseException(String key){
		this.key = key;
	}
	
	public BaseException(String key,Object... values){
		this.key = key;
		this.values = values;
	}
	
	public BaseException(Throwable cause,String key,Object... values){
		
		super((key==null || "".equals(key)?cause.getMessage():key),cause);
		this.key = key;
		this.values = values;
	}
	
	public BaseException(Throwable cause,String key){
		this(cause,key,(Object[])null);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	
}
