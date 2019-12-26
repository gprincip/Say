package com.say.say.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.say.say.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value="select * from user where username = :username",nativeQuery=true)
	User findByUsername(@Param(value = "username")String username);

}
