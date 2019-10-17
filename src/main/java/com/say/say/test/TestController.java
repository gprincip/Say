package com.say.say.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.util.Util;

@RestController
@RequestMapping(value="/test")
public class TestController {

	@RequestMapping(value="/testIpExtraction")
	public void getClientIp(HttpServletRequest request) {
		System.out.println(Util.extractIpFromServletRequest(request));
	}
	
}
