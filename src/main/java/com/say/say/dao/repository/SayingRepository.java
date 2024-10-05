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
	public List<Saying> getSayingsContainingAnyGivenTags(@Param(value="tagIds")List<Long> tagIds);
	
	@Query(value="select * from saying where text like concat('%',:searchTerm, '%')", nativeQuery=true)
	public List<Saying> getSayingsByText(@Param(value="searchTerm")String searchTerm);
	
	@Query(value="select saying_id\n" + 
			"from saying_tags\n" + 
			"group by saying_id \n" + 
			"having count(distinct case when tags_id in (:tagIds) then tags_id else 0 end) = :tagCount\n" + 
			"and min(case when tags_id in (:tagIds) then tags_id else 0 end) > 0", nativeQuery=true)
	public List<Long> getSayingsContainingExactlyGivenTags(@Param(value="tagIds")List<Long> tagIds, @Param(value="tagCount") String tagCount);
	
	@Query(value="select * from saying where user_id = :userId", nativeQuery = true)
	public List<Saying> getSayingsFromUserId(@Param(value="userId") long userId);
	
	@Query(value="select s.* from saying s join users u on s.user_id = u.id where u.username = :username", nativeQuery = true)
	public List<Saying> getSayingsFromUsername(@Param(value="username") String username);
	
	@Query(value="select * from saying where text like concat('%',:searchTerm, '%') limit :limit", nativeQuery = true)
	public List<Saying> getSayingsByTextLimited(@Param(value="searchTerm") String searchTerm, @Param(value="limit") int limit);
	
}
