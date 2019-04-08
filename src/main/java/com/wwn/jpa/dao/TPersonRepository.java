package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.TPerson;
import com.wwn.jpa.entity.UserInfo;
@Repository
@CacheConfig(cacheNames = "tPerson")
public interface TPersonRepository extends JpaRepository<TPerson, Integer>, CrudRepository<TPerson, Integer>{
	
	/*
	 * 通过用户名查看用户信息
	 * */
//	public UserInfo findByusername(String username);
	/*
	 * 修改用户密码
	 * */
//	public boolean updateByPassword(UserInfo userInfo);
	@Transactional
    @Modifying
    @Query(value = "delete from t_person where pid in (?1)",nativeQuery = true)
	Integer deletePerson(List<Integer> pidList);
	/*
     *  查询用户信息 模糊多查询
     * return  List<TPerson> list 
     * */
	 @Query(value = "SELECT * from t_person t where t.page like %?1% or t.pname like %?1% ORDER BY t.pid LIMIT ?2,?3",nativeQuery = true)
	 List<TPerson>   searchPerson(String field,Integer page ,Integer size);
	 
	 /*
      *  查询总记录数
      * return  Integer 
      * */
	 @Query(value = "SELECT COUNT(1)as total from t_person t where t.page like %?1% or t.pname like %?1% LIMIT ?2,?3",nativeQuery = true)
     public Integer getPersonCount(String field,Integer page ,Integer size);
	 /*
     *  分页加模糊匹配
     * return  List<TPerson> list 
     * */
	 @Cacheable
	 @Query(value = "SELECT * from t_person t where t.page like %?1% or t.pname like %?1% ORDER BY t.pid ",nativeQuery = true)
	 Page<TPerson>   searchPersonPag(String field,Pageable pageable );
	 
	 @Query(value ="SELECT   t.name from user_info t ",nativeQuery = true )
	 List<String> getUserName ();
}
