package com.digitalBook.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**")
		.allowedOrigins("https://localhost:8083", "http://localhost:8083", "http://3.37.64.204:8083")
		.allowedMethods("*")
		.allowCredentials(true);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("login/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}