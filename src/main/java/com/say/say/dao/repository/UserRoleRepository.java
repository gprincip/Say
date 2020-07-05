package com.say.say.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.say.say.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
}
