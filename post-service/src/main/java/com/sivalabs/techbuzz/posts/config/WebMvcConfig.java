package com.sivalabs.techbuzz.posts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/api/**")
	            .allowedMethods("*")
	            .allowedHeaders("*")
	            .allowedOriginPatterns("*")
	            .allowCredentials(true);
	}
}