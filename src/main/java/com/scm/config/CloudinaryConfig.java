package com.scm.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	
	@Bean
	public Cloudinary cloudinary()
	{
		Map config = new HashMap<>();
		config.put("cloud_name", "dwnva2fdu");
		config.put("api_key", "357579998929329");
		config.put("api_secret", "UOFInPZyNcORr0tneL-3--kRAUY");
		return new Cloudinary(config);
	}

}
