package com.wey.framework.util;

public class StringUtil {

	public static boolean isNull(String inValue){
		return null == inValue;
	}
	
	public static boolean isEmpty(String inValue){
		if(isNull(inValue)){
			return true;
		}
		
		return inValue.length()<1;
	}
	
	public static boolean isRealEmpty(String inValue){
		if(isEmpty(inValue)){
			return true;
		}
		return inValue.trim().length()<1;
	}
	
	public static boolean isDigit(String inValue){
		if(isRealEmpty(inValue)){
			return false;
		}
		
		return inValue.matches("^[0-9]+(.[0-9]+)$");
	}
	
	public static boolean isInteger(String inValue){
		if(isRealEmpty(inValue)){
			return false;
		}
		
		return inValue.matches("^[0-9]+$");
	}
	
	public static String connectURL(String...args){
		StringBuilder builder = new StringBuilder();
		boolean previous = false;
		for(String arg:args){
			if(!StringUtil.isRealEmpty(arg) && !"/".equals(arg.trim())){
				arg = arg.trim();
				if(previous && arg.startsWith("/")){
					arg = arg.substring(1);
				}
				
				if(!previous && !arg.startsWith("/")){
					arg = "/"+arg;
				}
				builder.append(arg);
				previous = arg.endsWith("/");
			}
		}
		return builder.toString();
	}
	
	public static String strip(String value){
		return strip(value,"");
	}
	
	public static String strip(String value,String defaultValue){
		if(isRealEmpty(value)){
			value = defaultValue;
		} else {
			value = value.trim();
		}
		return value;
	}
}
