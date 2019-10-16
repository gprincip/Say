package com.say.say.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.service.SayingService;
import com.say.say.util.Util;

/**
 * Controller used for adding data to thymeleaf templates
 * @author gavrilo
 *
 */
@Controller
public class ThymeleafController {

	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	SayingService sayingService;
	
	@RequestMapping(path="/testThymeleaf")
	public ModelAndView testThymeleaf() {
		
		System.out.println("AAAAAAAAAAAAAAAAAAaaaa");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/temp");
		
		List<Saying> sayings = sayingRepo.findAll();
		mav.addObject("sayings", sayings);
		
		return mav;
	}
	
	@RequestMapping(path="/test")
	public String test(Model model) {
		
		model.addAttribute("sayings", sayingRepo.findAll());
		return "temp";
		
	}
	
	@RequestMapping(path="/sayings")
	public ModelAndView getSayingsView() {
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sayings");
		
		//mav.addObject("sayings", null);
		
		return mav;
	}
	/**
	 * Method for working with request sent by html form for submitting a new saying
	 * @param saying content of the form
	 * @return
	 */
	@RequestMapping(path="/processSayingFromForm", method=RequestMethod.POST)
	public ModelAndView processSayingFromForm(@RequestParam(name="saying") String saying, @RequestParam(name="tagSet") Set<String> tags
			,HttpServletRequest req) {
		
		String clientIp = Util.extractIpFromServletRequest(req);
		
		sayingService.persistSaying(saying, tags, clientIp);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:sayings");
		return mav;
		
	}
	
}
