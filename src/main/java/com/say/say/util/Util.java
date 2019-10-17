package com.say.say.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	private static final Logger log = LoggerFactory.getLogger(Util.class);

	private static final String HTTP_HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

	public static String extractIpFromServletRequest(HttpServletRequest request) {
		
		 String remoteAddr = "";

	        if (request != null) {
	            remoteAddr = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
	            if (remoteAddr == null || "".equals(remoteAddr)) {
	                remoteAddr = request.getRemoteAddr();
	            }
	        }

	        return remoteAddr;
		
	}
	
	public static Map<String,String> loadPropertiesFileIntoMap(File props){
		
		Map<String,String> propsMap = new HashMap<String,String>();
		
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(props);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(scanner != null) {
			
			while(scanner.hasNextLine()){
				
				String line = scanner.nextLine();
				String keyVal[] = line.split("=");
				if(keyVal.length == 2) {
					propsMap.put(keyVal[0], keyVal[1]);
				}else if(!keyVal[0].startsWith("#")) {
					log.error("Error parsing key-value pair: " + line + ". It will be skipped.");
				}
				
			}
			
		}
		
		scanner.close();
		
		return propsMap;
	}
	
}
