package com.say.say.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.say.say.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query(value = "select * from role where role = :roleName", nativeQuery = true)
	public Role findRoleByName(@Param(value = "roleName")String roleName);
	
}
