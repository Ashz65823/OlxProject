package com.zensar.olx.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @author Ashwini
 * This configuration file is needed for customizing CORS configuration
 * CORS= Cross Origin Resource Sharing
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {//interface allow to do configuration changes here for cors we do

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**")
	.allowedMethods("GET","DELETE","PUT","OPTIONS","POST")
	.allowedOrigins("http://localhost:4200");
	}
}
