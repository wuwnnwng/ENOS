package com.wwn.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwn.jpa.dao.UserInfoRepository;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.UserInfoService;

import sun.misc.BASE64Decoder;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	 protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserInfo findByusername(String username) {
		UserInfo userInfo = userInfoRepository.findByusername(username);
		return userInfo;
	}

	@Override
	public String checkUser(String name) {
		String username=userInfoRepository.checkUser(name);
		return username;
	}
//	@Transactional
	@Override
	public  Map<String,Object> wjmm(String username, String userpassword) {
		logger.info("wjmm begin");
		Map<String,Object> map = new HashMap<>();
		UserInfo uf = userInfoRepository.wjmm(username);
		if(uf==null) {//用户不存在
			map.put("xgmsg", "用户不存在");
			return map;
		}
		 /*base64解密*/
		  BASE64Decoder decoder = new BASE64Decoder();
		  try {
		     byte[] key = decoder.decodeBuffer(userpassword);
		     userpassword = new String(key);
		  } catch (Exception e) {
			  map.put("xgmsg", "修改密码错误请联系管理员");
			  return map;
		  }
		//对传过来的密码加密 3a4ebf16a4795ad258e5408bae7be341
		  Md5Hash md5 = new  Md5Hash(userpassword,uf.getUsername(),1);
//		  System.out.println(md5);
		//将数据库的密码修改
		  Integer chageRow = userInfoRepository.xgmm(username,md5.toString());
		  if(chageRow>0) {
			  map.put("xgmsg", "修改密码成功");
			  return map;
		  }
		return map;
	}

	@Override
	public UserInfo save(UserInfo uf) throws Exception {
		logger.info("save begin");
		UserInfo userInfo = userInfoRepository.save(uf);
		return userInfo;
	}

}
