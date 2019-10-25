package com.say.say.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.say.say.model.Saying;

@Repository
public interface SayingRepository extends JpaRepository<Saying, Long>{
 
	/**
	 * @param clientIp
	 * @return last saying that was commited from some IP address
	 */
	@Query(value="select * from saying where client_ip = :ip order by date DESC limit 1", nativeQuery=true)
	public Saying getLastSayingFromIp(@Param(value="ip")String clientIp);
	
	@Query(value="select distinct * from saying s where id in ("
			+ "select saying_id from saying_tags where saying_id = s.id "
			+ "and tags_id in (:tagIds))", nativeQuery=true)
	public List<Saying> getSayingsWithTags(@Param(value="tagIds")List<Long> tagIds);
	
}
