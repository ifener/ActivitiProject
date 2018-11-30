package com.wey.framework.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 日期字符串(YYYY-MM-DD - YYYY-MM-DD)转成日期数组
 * 
 * @author weisunqing
 *
 */
public class DateRangeConverter implements Converter<String, Date[]> {

	@Override
	public Date[] convert(String source) {
		Date[] dates = null;
		if (StringUtils.hasText(source)) {
			String[] datestrs = source.split(" - ");
			if (datestrs.length == 2) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.setLenient(false);
				try {
					dates = new Date[2];

					dates[0] = dateFormat.parse(datestrs[0]);
					dates[1] = dateFormat.parse(datestrs[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return dates;
	}

}
