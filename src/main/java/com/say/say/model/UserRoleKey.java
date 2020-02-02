package com.say.say.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class UserRoleKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role roleId;
	
}
