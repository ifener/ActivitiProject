package com.wey.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	/*
	 * 将异常的堆栈信息转成String
	 */
	public static String getExceptionStack(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}
}
