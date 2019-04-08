package com.wwn.service;

import java.util.Map;

import com.wwn.jpa.entity.UserInfo;

public interface UserInfoService {
	/*
	 * 通过用户名查看用户信息
	 * return UserInfo
	 * date 2019/03/20
	 * */
	public UserInfo findByusername(String username );
	/*wjmm
	 * 检查用户是不是存在
	 * return UserInfo
	 * date 2019/03/20
	 * */
	public String checkUser(String name );
	
	/*
	 * 修改密码
	 * return boolean
	 * date 2019/03/22
	 * */
	public Map<String, Object> wjmm(String username,String userpassword);
	/*
	 * 注册用户
	 * return UserInfo
	 * date 2019/03/22
	 * */
	public UserInfo save(UserInfo uf) throws Exception;
}
