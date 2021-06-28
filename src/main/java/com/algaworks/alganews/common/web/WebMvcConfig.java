package com.algaworks.alganews.common.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new YearMonthConverter());
	}
	
}

