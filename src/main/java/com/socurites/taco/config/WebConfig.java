package com.socurites.taco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		WebMvcConfigurer.super.addViewControllers(registry);
		
		registry.addViewController("/test-path").setViewName("home");
		registry.addRedirectViewController("/test-path2", "/");

		registry.addViewController("/login");
		registry.addViewController("/login-fail");
	}
	
	
	
}
