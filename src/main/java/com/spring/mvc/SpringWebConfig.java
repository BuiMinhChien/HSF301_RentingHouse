/**
 * 
 */
package com.spring.mvc;


import com.spring.mvc.service.EmailService;
import com.spring.mvc.service.impl.EmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * 
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan(basePackages = "com.spring.mvc")
@EnableTransactionManagement
public class SpringWebConfig implements WebMvcConfigurer, ApplicationContextAware {
	private ApplicationContext applicationContext;

    @Bean
	SpringResourceTemplateResolver templateResolver() {
		// SpringResourceTemplateResolver automatically integrates with Spring's own
		// resource resolution infrastructure, which is highly recommended.
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		// HTML is the default value, added here for the sake of clarity.
		templateResolver.setTemplateMode(TemplateMode.HTML);
		// Template cache is true by default. Set to false if you want
		// templates to be automatically updated when modified.
		templateResolver.setCacheable(true);
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}

	@Bean
	SpringTemplateEngine templateEngine() {
		// SpringTemplateEngine automatically applies SpringStandardDialect and
		// enables Spring's own MessageSource message resolution mechanisms.
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		// Enabling the SpringEL compiler with Spring 4.2.4 or newer can
		// speed up execution in most scenarios, but might be incompatible
		// with specific cases when expressions in one template are reused
		// across different data types, so this flag is "false" by default
		// for safer backwards compatibility.
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setContentType("text/html; charset=UTF-8"); // Đảm bảo trả về UTF-8
		return viewResolver;
	}


	/* ******************************************************************* */
	/* GENERAL CONFIGURATION ARTIFACTS */
	/* Static Resources, i18n Messages, Formatters (Conversion Service) */
	/* ******************************************************************* */

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/assets_CustomerSide/**").addResourceLocations("classpath:/static/assets_CustomerSide/");
		registry.addResourceHandler("/dashboardStatic/**").addResourceLocations("classpath:/static/dashboardStatic/");
		registry.addResourceHandler("/document/**").addResourceLocations("classpath:/static/document/");
		registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
	}

	@Bean
	ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("Messages");
		return messageSource;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public OpenSessionInViewFilter openSessionInViewFilter() {
		return new OpenSessionInViewFilter();
	}


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Thêm Jackson converter để hỗ trợ JSON
		converters.add(new MappingJackson2HttpMessageConverter());
		// Có thể thêm các converter khác ở đây nếu cần
	}

	@Bean
	public EmailService emailService() {
		// Khởi tạo EmailServiceImpl với email và mật khẩu của bạn
		return new EmailServiceImpl("fapbmcprj301@gmail.com", "vzfqauqysltljfjc");
	}
}