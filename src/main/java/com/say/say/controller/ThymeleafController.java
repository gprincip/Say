package com.say.say.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.model.Saying;

/**
 * Controller used for adding data to thymeleaf templates
 * @author gavrilo
 *
 */
@Controller
public class ThymeleafController {

	@Autowired
	SayingRepository sayingRepo;
	
	@RequestMapping(path="/testThymeleaf")
	public ModelAndView testThymeleaf() {
		
		System.out.println("AAAAAAAAAAAAAAAAAAaaaa");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("temp");
		
		List<Saying> sayings = sayingRepo.findAll();
		mav.addObject("sayings", sayings);
		
		return mav;
	}
	
	@RequestMapping(path="/test")
	public String test(Model model) {
		
		model.addAttribute("sayings", sayingRepo.findAll());
		return "temp";
		
	}
	
}
