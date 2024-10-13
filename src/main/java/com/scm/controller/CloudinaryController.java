package com.scm.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.scm.services.CloudinaryService;

@Controller
public class CloudinaryController {

	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private Cloudinary cloudinary;
	
//	@GetMapping()
//	@ResponseBody
//	public String greeting()
//	{
//		return "Hello";
//	}
//	
//	@PostMapping("/do-upload")
//	public String uploadmage(@RequestParam("image") MultipartFile file, Model model) throws IOException
//	{
//		Map data = this.cloudinaryService.uploadFile(file);
//		model.addAttribute("ImageUrl", data.get("secure_url"));
//		return "profile";
//	}
//	
//	@GetMapping("/image")
//	public String getImage(Model model) throws IOException
//	{
//		String ImageUrl = "https://res.cloudinary.com/dwnva2fdu/image/upload/v1727717939/fwjzw5pvf1awgtzihahn.png";
//		model.addAttribute("ImageUrl",ImageUrl );
//		return "profile";
//	}
}
