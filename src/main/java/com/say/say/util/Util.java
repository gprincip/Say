package com.say.say.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.say.say.model.Saying;
import com.say.say.model.UserBean;

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
	
	public static Saying jsonToSaying(String json) {
		
		Gson gson = new Gson();
		Saying saying = gson.fromJson(json, Saying.class);
		return saying;
		
	}

	public static void setUserdata(UserBean user, HttpServletRequest request) {
		
		if(isNotInitialized(user)) {
			initializeUser(user, request);
		}
		
	}

	/**
	 * Method used to populate session scoped user data
	 * @param user
	 * @param request
	 */
	private static void initializeUser(UserBean user, HttpServletRequest request) {
		String userIp = extractIpFromServletRequest(request);
		user.setUserIp(userIp);
	}

	/**
	 * Checks if user has been initialized
	 * @param user
	 * @return
	 */
	private static boolean isNotInitialized(UserBean user) {
		
		if(StringUtils.isEmpty(user.getUserIp())){
			return true;
		}else return false;
		
	}

	public static List<Long> csvToLongs(String tagIdsCsv) {

		String[] splitted = tagIdsCsv.split(",");
		List<Long> longList = new ArrayList<Long>();

		for (String str : splitted) {
			try {
				Long lng = Long.parseLong(str.trim());
				longList.add(lng);
			} catch (Exception e) {
				log.warn("String :" + str + "couldn't be parsed to long, it will be skipped!");
			}
		}

		return longList;
	}
	
	
}
