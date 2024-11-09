package com.spring.mvc;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * 
 */
public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	public static final String CHARACTER_ENCODING = "UTF-8";
	private static final String UPLOAD_DIRECTORY = "D:\\Major 5\\HSF301\\HSF301_RentingHouse\\src\\main\\resources\\static\\image";
	private static final long MAX_FILE_SIZE = 5242880; // 5MB
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB
	private static final int FILE_SIZE_THRESHOLD = 0;
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
		return new Class<?>[]{SpringWebConfig.class};
	}
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		// Cấu hình MultipartConfigElement để cấu hình upload file
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
				UPLOAD_DIRECTORY,
				MAX_FILE_SIZE,
				MAX_REQUEST_SIZE,
				FILE_SIZE_THRESHOLD
		);
		registration.setMultipartConfig(multipartConfigElement);
	}
}
