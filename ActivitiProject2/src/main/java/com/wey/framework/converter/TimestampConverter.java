package com.wey.framework.converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class TimestampConverter implements Converter<String, Timestamp> {

	@Override
	public Timestamp convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		dateFormat.setLenient(false);
		try {
			Date parse = dateFormat.parse(source);
			return new Timestamp(parse.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
