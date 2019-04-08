package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.UserInfo;
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer>   {
	
	/*
	 * 通过用户名查看用户信息
	 * */
	public UserInfo findByusername(String username);
	/*
	 * 检查用户是不是存在
	 * return UserInfo 
	 * date 2019/03/20
	 * */
	 @Query(value = "SELECT t.username from user_info t where t.`name`=?1",nativeQuery = true)
  	public String checkUser(String name );
     /*
	 * 修改密码之查询用户信息
	 * return UserInfo 
	 * date 2019/03/22
	 * */
	 @Query(value = "SELECT t.`name`,t.`password`,t.username,t.uid,t.salt ,t.state ,t.age,t.phone,t.email,t.depart_id,t.sex from user_info t where t.`name`=?1",nativeQuery = true)
	public UserInfo wjmm(String username );
  	
  	 /*
	 * 修改密码 
	 * return Integer 
	 * date 2019/03/22
	 * */ 
	 @Transactional
	 @Modifying
     @Query(value = "UPDATE user_info t set t.`password`=?2 where t.`name`=?1",nativeQuery = true)
    public Integer xgmm(String username,String md5);

	 /*
	 * 等到员工新姓名
	 * String 
	 * date 2019/03/27
	 * */
	 @Query(value ="SELECT t.name from user_info t ",nativeQuery = true )
	 List<String>  getUserName ();	 
	 
	 
}
