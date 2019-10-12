package com.say.say.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.say.say.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	@Query(value="select * from tag where name = :name",nativeQuery=true)
	public Tag findTagByName(@Param("name") String name);
	
}
