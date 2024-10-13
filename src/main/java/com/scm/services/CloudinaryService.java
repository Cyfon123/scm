package com.scm.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	
	public Map uploadFile(MultipartFile file) throws IOException;

}
