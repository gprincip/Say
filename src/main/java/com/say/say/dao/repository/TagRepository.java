package com.say.say.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.say.say.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

}
