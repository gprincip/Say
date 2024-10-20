package com.say.say.controller.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.say.say.config.ApplicationProperties;
import com.say.say.dao.SayingDaoDb;
import com.say.say.dao.repository.TagRepository;
import com.say.say.model.LoggedUser;
import com.say.say.model.Saying;
import com.say.say.model.Tag;
import com.say.say.service.SayingService;
import com.say.say.service.UserService;
import com.say.say.util.JsonUtil;
import com.say.say.util.TimeUtil;

@RestController
@RequestMapping(path="/api/saying")
public class SayingController {

	@Autowired
	SayingDaoDb sayingDao;
	
	@Autowired
	SayingService sayingService;
	
	@Autowired
	TagRepository tagRepo;
	
	/**
	 * Current user
	 */
	@Autowired
	LoggedUser user;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApplicationProperties config;
	
	@RequestMapping(path="/testSave")
	public void testSave() {
		Saying s = new Saying("To be or not to be", "Pera", null, 0, null, null);
		
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(new Tag("Sport"));
		tags.add(new Tag("Formula 1"));
		
		for(Tag t : tags) {
			tagRepo.save(t);
		}
		
		s.setTags(tags);
		sayingDao.save(s);
		System.out.println("Saved: " + s);
	}
	
	@RequestMapping(path="/findAll")
	public List<Saying> findAll(HttpServletRequest request){
		return sayingDao.findAll();
	}
	
	/**
	 * Validate and save saying
	 * @param sayingJson
	 * @return status after validation (error messages etc..)
	 */
	@RequestMapping(path="/validateAndSaveSaying", consumes={"application/json"}, method=RequestMethod.POST)
	public String validateAndSaveSaying(@RequestBody String sayingJson) {
		
		String userIp = user.getUserIp();
		Long userId = user.getUser().getId();
		String waitingTimeInMilliseconds = userService.timeUntilPostingCooldownExpired(userId);
		Saying saying = JsonUtil.jsonToSaying(sayingJson);
		saying.setUser(user.getUser());
		
		
		String timeUnits = config.getPostingCooldownFormat();
		int numUnits = Integer.parseInt(config.getNumberOfUnitsForCooldownTimeDisplay());
		if(!(waitingTimeInMilliseconds).equals("0")) {
			return "You must wait " + TimeUtil.formatCooldownTimeInMsForDisplay(waitingTimeInMilliseconds, TimeUtil.CsvTimeUnitsToList(timeUnits), numUnits) + " until you can post again!";
		}
		
		if(saying.getTags() == null || saying.getTags().size() == 0) {
			return "You mast add at least one tag!";
		}
		
		if(saying.getText() == null || StringUtils.isBlank(saying.getText())) {
			return "Text cannot be empty or blank!";
		}
		
		sayingService.persistSaying(saying, userIp);
		sayingService.addSayingToUserSayingsCache(saying, user.getUser().getId());
		
		return "";
	}
	
	@RequestMapping(path="/getAllSayingsFromUser", method = RequestMethod.GET)
	public List<Saying> getAllSayingsFromUser(@RequestParam(name = "userId") long userId){
		return sayingDao.getSayingsFromUserId(userId);
	}
	
}
