package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wwn.jpa.entity.Qjlx;
@Repository
@CacheConfig(cacheNames = "qjlx")
public interface QjlxRepository extends JpaRepository<Qjlx, Integer>, CrudRepository<Qjlx, Integer>{
	
   @Cacheable 
   @Query(value ="SELECT  t.ql_id,t.ql_name from  t_qjlx t ",nativeQuery = true )
   List<Qjlx> getQjlx ();
}
