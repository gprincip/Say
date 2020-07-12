package com.say.say.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.say.say.service.StorageService;

@RestController
public class FileUploadController {

	@Autowired
	StorageService fileSystemStorageService;
	
	@RequestMapping(path = "upload/")
	public void uploadFile(MultipartFile file) {
		
		fileSystemStorageService.store(file);
		
	}
	
}
