package com.say.say.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserRole {

	@EmbeddedId
	UserRoleKey userRoleKey;
	
}
