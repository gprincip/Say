package com.say.say.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.say.say.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value="select * from user where username = :username",nativeQuery=true)
	User findUserByUsername(@Param(value = "username")String username);

	@Query(value="select * from user where email = :email", nativeQuery = true)
	List<User> findUserByEmail(@Param(value="email") String email);
	
}
