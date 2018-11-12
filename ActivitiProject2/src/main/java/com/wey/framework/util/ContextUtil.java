package com.wey.framework.util;

public class ContextUtil {
	
	private static ThreadLocal<Context> contexts = new ThreadLocal<Context>();
	
	public static Context getContext(){
		return (Context)contexts.get();
	}
	
	public static void setContext(Context context){
		contexts.set(context);
	}

}
