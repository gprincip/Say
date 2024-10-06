package com.say.say.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.say.say.config.ApplicationProperties;

/**
 * Implementation of storage service used for storing uploaded files on disk
 * @author gaad2107
 *
 */
@Service
public class FileSystemStorageService implements StorageService{

	Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);
	
	@Autowired ApplicationProperties config;

	@Override
	public void store(MultipartFile file) {
		
		File fileOnDisk = new File(config.getProperty("upload.defaultUploadPath"));
		InputStream fis = null;
		try {
			fis = file.getInputStream();
		} catch (IOException e) {
			log.error("Error while opening opening input stream from a multipart file!", e);
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileOnDisk);
		} catch (FileNotFoundException e1) {
			log.error("Error while opening output stream to write to file!", e1);
		}

		writeToFile(fis, fos);
		
	}

	private void writeToFile(InputStream fis, FileOutputStream fos) {
		if(fis != null && fos != null) {
			try {
				int readByte;
				while((readByte = fis.read()) != -1) {
					fos.write(readByte);
				}
			} catch (IOException e) {
				log.error("Error while reading bytes from a multipart file!", e);

			}
		}
	}

}
