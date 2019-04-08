package com.wwn.jpa.dao;

import java.util.List;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.wwn.jpa.entity.Depart;
@Repository
@CacheConfig(cacheNames = "depart")
public interface DepartRepository extends JpaRepository<Depart, Integer>, CrudRepository<Depart, Integer>{
	
   @Cacheable 
   @Query(value ="SELECT  t.d_id,t.d_name from  t_depart t ",nativeQuery = true )
   List<Depart> getDepart ();
}
