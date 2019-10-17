package com.say.say.dao.repository;

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
	
}
