package com.wey.framework.converter;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

/**
 * 将日期转成String到JSP页面显示
 * 
 * @author weisunqing
 *
 */
public class DateRangeToStringConverter implements Converter<Date[], String> {

	@Override
	public String convert(Date[] source) {
		StringBuilder builder = new StringBuilder();
		if (source != null) {
			if (source.length == 2) {
				try {
					builder.append(new DateTime(source[0].getTime())
							.toString(com.wey.framework.converter.DateConverter.FORMATTER));
					builder.append(" - ");
					builder.append(new DateTime(source[1].getTime())
							.toString(com.wey.framework.converter.DateConverter.FORMATTER));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

}
