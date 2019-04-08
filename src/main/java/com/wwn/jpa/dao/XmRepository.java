package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.wwn.jpa.entity.Xm;
@Repository
//@CacheConfig(cacheNames = "depart")
public interface XmRepository extends JpaRepository<Xm, Integer>, CrudRepository<Xm, Integer>{
	
//   @Cacheable 
   @Query(value ="SELECT  t.xm_id,t.xm_name from  t_xm t ",nativeQuery = true )
   List<Xm> getXm();
}
