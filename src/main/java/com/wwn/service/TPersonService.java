package com.wwn.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.TPerson;

public interface TPersonService {

	/*
	 * 通过用户名查看用户信息
	 * */
//	public UserInfo findByusername(String username );
	 /**
     * 用户列表
     * 
     * @return 返回用户对象列表
     */
//    public List<User> list();
     public Page<TPerson> find(Integer page, Integer size);
	  /*
	   * 根据用户的id 删除用户信息
	   * */
     public Integer deletePerson(List<Integer> pidList);
     
     /*
      * 保存或者修改用户,当用户的领id  不为空的时候修改用户信息，反之就是新增用户信息
      * return  map
      * */
     public Map<String,Object> save(TPerson tPerson);
     
     /*
      *  查询用户信息 模糊多查询
      * return  list 
      * */
     public List<TPerson> searchPerson(String field,Integer page ,Integer size);
    
     /*
      *  查询总记录数
      * return  Integer 
      * */
     public Integer getPersonCount(String field,Integer page ,Integer size);
     
    
}
