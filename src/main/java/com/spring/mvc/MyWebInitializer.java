package com.spring.mvc;

import jakarta.servlet.Filter;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 */
public class MyWebInitializer 
extends AbstractAnnotationConfigDispatcherServletInitializer {
	public static final String CHARACTER_ENCODING = "UTF-8";
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class, DatabaseConfig.class };
	}

	@Override
	@NonNull
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	@Override
	protected Filter[] getServletFilters() {
		final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(CHARACTER_ENCODING);
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}
}
