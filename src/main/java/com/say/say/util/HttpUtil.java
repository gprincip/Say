package com.say.say.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {

	/**
	 * Executes http post request and returns http response
	 * @param url to be executed
	 * @param headers
	 * @return
	 */
	public static HttpResponse makePostRequest(String url, Map<String, String> headers) {
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postReq = new HttpPost(url);
		
		handleHeaders(headers, postReq);
		
		try {
			return httpClient.execute(postReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private static void handleHeaders(Map<String, String> headers, HttpPost postReq) {
		
		if(headers != null && headers.size() > 0) {
			
			for(Map.Entry<String, String> entry : headers.entrySet()) {
				
				postReq.addHeader(entry.getKey(), entry.getValue());
				
			}
			
		}
	}
	
}
