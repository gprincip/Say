package com.say.say.model;

import java.io.Serializable;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.say.say.dao.repository.UserRepository;
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
	SqlExecutorService sqlExecutorService;
	
	private String userIp;
	private String username;
	private User user;
	
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

	public User getUser() {
		
		if(user == null && StringUtils.isNotBlank(username)) {
			user = userRepo.findUserByUsername(username); //load user from DB
		}
		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
