package com.say.say.test;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.say.say.model.Saying;
import com.say.say.model.SayingsSearchParameters;
import com.say.say.model.UserBean;
import com.say.say.search.ISearcher;
import com.say.say.search.SayingsSearchResult;
import com.say.say.service.RedisService;
import com.say.say.service.SayingSearcher;
import com.say.say.service.SayingService;
import com.say.say.service.email.MailSenderWrapper;
import com.say.say.sql.SqlExecutorService;
import com.say.say.sql.SqlReader;
import com.say.say.util.JsonUtil;
import com.say.say.util.Util;

@RestController
@RequestMapping(value="/test")
public class TestController {

	@Autowired
	SayingSearcher searcher;
	
	@Autowired
	ISearcher dbSearcher;
	
	@Autowired
	SayingService sService;
	
	@Autowired
	UserBean userBean;
	
	@Autowired
	MailSenderWrapper mailSender;
	
	@Autowired
	SqlReader sqlReader;
	
	@Autowired
	SqlExecutorService sqlExecutor;
	
	@Autowired
	RedisService redisService;
	
	@RequestMapping(value="/testIpExtraction")
	public void getClientIp(HttpServletRequest request) {
		System.out.println(Util.extractIpFromServletRequest(request));
	}

	/**
	 * Finds all sayings that contains specified tags
	 * @param sayingId
	 * @param tagIds comma separated tagIds
	 */
	@RequestMapping(value="/testSearch")
	public void getTestSearch(@RequestParam(value="tagIds") String tagIdsCsv) {
		
		List<Long> tagIds = Util.csvToLongs(tagIdsCsv);
		List<Saying> result = searcher.searchSayingsContainingAnyGivenTags(tagIds);
		System.out.println(result);
		
	}
	
	@RequestMapping(value="/testSearch2")
	public List<Long> getSayingsWithExactTags(@RequestParam(value="tagIds") String tagIdsCsv) {
		
		List<Long> tagIds = Util.csvToLongs(tagIdsCsv);
		List<Long> result = searcher.searchSayingsContainingExactlyGivenTags(tagIds);
		return result;
	}
	
	@RequestMapping(value="/search/sText")
	public String sayingsByText(@RequestParam(value="searchTerm") String searchTerm, @RequestParam(value="fetchQuantity") Integer fetchQuantity) {
		SayingsSearchResult result = dbSearcher.searchSayingsByText(new SayingsSearchParameters(searchTerm, fetchQuantity));
		return JsonUtil.searchResultToJson(result);
	}
	
	@RequestMapping(value="/search/sText2")
	public List<Saying> sayingsByText2(@RequestParam(value="searchTerm") String searchTerm) {
		
		return sService.getSayingsByTextLimited("q", 4);
		
	}
	
	@RequestMapping(value="/getPrincipal")
	public String getprincipal(Principal p) {
		return p.getName();
	}
	
	@RequestMapping(value="/sendEmail")
	public void sendEmail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gprincip132@gmail.com");
		message.setTo("pcelalala@gmail.com");
		message.setSubject("Test subject");
		message.setText("Test text");
		
		mailSender.send(message);
		
	}
	
	@RequestMapping(value="/getSql")
	public String getSql(@RequestParam(value = "key") String key) {
		return sqlReader.getSqlQuery(key);
	}
	
	@RequestMapping(value="/testSqlExecutor")
	public void testSqlExecutor() {
		sqlExecutor.test();
	}
	

	@RequestMapping("/testRedis")
	public void testRedis() {
		redisService.testRedis();
	}
	
}





