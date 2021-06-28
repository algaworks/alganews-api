package com.algaworks.alganews.common.web;

import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthConverter implements Converter<String, YearMonth> {
	
	@Override
	public YearMonth convert(String text) {
		return YearMonth.parse(text, DateTimeFormatter.ofPattern("yyyy-MM"));
	}
	
}
