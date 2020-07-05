package com.say.say.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.say.say.dao.repository.RoleRepository;
import com.say.say.dao.repository.UserRoleRepository;
import com.say.say.model.Role;
import com.say.say.model.User;
import com.say.say.model.UserRole;
import com.say.say.model.UserRoleKey;
import com.say.say.model.UserRolesEnum;

@Service
public class UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	public void setRoleToUser(User user, Role role) {
		
		UserRole userRole = new UserRole();
		UserRoleKey userRoleKey = new UserRoleKey();
		
		userRoleKey.setUserId(user);
		userRoleKey.setRoleId(role);
		
		userRole.setUserRoleKey(userRoleKey);
		
		userRoleRepo.save(userRole);
		
	}
	
	
	/**
	 * Method that returns user role from database based on role name. For every new role, update this method to work with newly added roles.</br>
	 * UserRolesEnum.enum enums must have the same name as the entries in database.
	 * @param role
	 * @return
	 */
	public Role getUserRole(UserRolesEnum role) {
		
		switch(role) {
		case ROLE_ADMIN : return roleRepo.findRoleByName(UserRolesEnum.ROLE_ADMIN.name());
		case ROLE_USER : return roleRepo.findRoleByName(UserRolesEnum.ROLE_USER.name());
		}
		return null;
	}

	
}
