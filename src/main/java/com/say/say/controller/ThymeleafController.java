package com.say.say.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.Saying;
import com.say.say.model.UserBean;
import com.say.say.service.SayingService;

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
	
	@Autowired
	UserBean userBean;
	
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
		
		mav.addObject("userBean", userBean);
		//Test t = new Test();
		//t.test();
		return mav;
	}
	

	@RequestMapping(path="/sayings/{username}")
	public ModelAndView getUserHomepage(String sayingId, @PathVariable(name = "username") String username) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sayings");
		
		userBean.populateSayingsFromCurrentUser();
		
		mav.addObject("userBean", userBean);
		
		return mav;
	}
	
	@RequestMapping(path="/sayings/{username}/{sayingId}")
	public ModelAndView getSingleSaying(@PathVariable(name = "sayingId") String sayingId, @PathVariable(name = "username") String username) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sayingTemplate");

		Long id = Long.parseLong(sayingId);
		
		Optional<Saying> loadedSaying = sayingRepo.findById(id);
		if(loadedSaying.isPresent()) {
			mav.addObject("saying", loadedSaying.get());
		}
		
		
		return mav;
		
	}
	
	@RequestMapping(path="/login")
	public String getLogin() {
		return "login";
	}

	@RequestMapping(path="/register")
	public String getRegister() {
		return "register";
	}
	
}

