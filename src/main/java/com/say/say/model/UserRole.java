package com.say.say.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserRole {

	@EmbeddedId
	private UserRoleKey userRoleKey;

	public UserRoleKey getUserRoleKey() {
		return userRoleKey;
	}

	public void setUserRoleKey(UserRoleKey userRoleKey) {
		this.userRoleKey = userRoleKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userRoleKey == null) ? 0 : userRoleKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		if (userRoleKey == null) {
			if (other.userRoleKey != null)
				return false;
		} else if (!userRoleKey.equals(other.userRoleKey))
			return false;
		return true;
	}
	
}
