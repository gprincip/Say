package com.say.say.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.say.say.config.GlobalConfig;
import com.say.say.dao.repository.SayingRepository;
import com.say.say.dao.repository.UserRepository;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategy;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategyAll;
import com.say.say.sayings.displayStrategy.SayingsDisplayStrategyFetchQuantity;
import com.say.say.sql.SqlExecutorService;

/**
 * Session scope bean that holds info about current logged in user
 * @author gavrilo
 *
 */
public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SayingRepository sayingRepo;
	
	@Autowired
	SqlExecutorService sqlExecutorService;
	
	@Autowired
	SayingsDisplayStrategy sayingsDisplayStrategy;
	
	private String userIp;
	private String username;
	private User user;
	/** List of all sayings of current user */
	private List<Saying> sayings;
	/** Subset of sayings to be displayed on view */
	private List<Saying> sayingsForDisplay;
		
	public UserBean() {}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userIp == null) ? 0 : userIp.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Saying> getSayings() {
		return sayings;
	}

	public void setSayings(List<Saying> sayings) {
		this.sayings = sayings;
	}

	public List<Saying> getSayingsForDisplay() {
		return sayingsForDisplay;
	}

	public void setSayingsForDisplay(List<Saying> sayingsForDisplay) {
		this.sayingsForDisplay = sayingsForDisplay;
	}

	public User getUser() {
		
		if(user == null && StringUtils.isNotBlank(username)) {
			user = userRepo.findUserByUsername(username); //load user from DB
		}
		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public void populateSayingsFromCurrentUser() {
		
		if(sayings == null) {
			sayings = new ArrayList<Saying>();
		}
		
		List<Saying> sayings = sayingRepo.getSayingsFromUsername(username);
		setSayings(sayings);
		
		if(sayingsForDisplay == null) {
			sayingsForDisplay = new ArrayList<Saying>();
		}
		
		if(sayingsDisplayStrategy == null) {
			loadSayingsDisplayStrategy();
		}
		sayingsForDisplay = sayingsDisplayStrategy.selectSayings(sayings);
		
	}
	
	public SayingsDisplayStrategy getSayingsDisplayStrategy() {
		return sayingsDisplayStrategy;
	}

	public void setSayingsDisplayStrategy(SayingsDisplayStrategy sayingsDisplayStrategy) {
		this.sayingsDisplayStrategy = sayingsDisplayStrategy;
	}

	private void loadSayingsDisplayStrategy() {

		/*
		 * String strategy =
		 * config.getProperty("sayings.displayStrategy.usedDisplayStrategy");
		 * 
		 * switch (strategy) { case "fetchQuantity": sayingsDisplayStrategy = new
		 * SayingsDisplayStrategyFetchQuantity(); break; case "all":
		 * sayingsDisplayStrategy = new SayingsDisplayStrategyAll(); break; }
		 */

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userIp == null) {
			if (other.userIp != null)
				return false;
		} else if (!userIp.equals(other.userIp))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


}
