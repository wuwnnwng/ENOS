package com.wwn.controller;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.parser.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.UserInfoService;
import sun.misc.BASE64Decoder;
@Controller
public class LoginController {
	 protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	UserInfoService userInfoService;
	@RequestMapping(value="/login")
	public String login( String name,boolean  rememberMe,String password,Model model, 
			            HttpServletRequest request, HttpSession session) throws Exception{
	  
	  logger.info("login begin");
	  /*获取Cookie中的rememberme 如果不为空就不需要登录直接到主页*/
	  javax.servlet.http.Cookie[] cookies = request.getCookies();
	  if(cookies!=null) {
		  for (javax.servlet.http.Cookie cookie : cookies) {
				if( cookie.getName().equals("rememberMe")) {//说明当前用户已经登录过无需再次登录
					 Subject remembermeSubject = SecurityUtils.getSubject();
				     UserInfo remembermeUserInfo = (UserInfo) remembermeSubject.getPrincipal();
				     session.setAttribute("userInfo", remembermeUserInfo);
					 return "index";
				}
			  }
	  }
//	  String rememberMe = 
	  if(name==null||name.equals("")||password==null||password.equals("")) {
//		  model.addAttribute("msg", "用户名或者密码为空");
//		  session.setAttribute("msg","用户名或者密码为空");
//		  return "redirect:/loginPage";
		  return "login";
	  }	
	  //判断用户是不是存在
	  String username = userInfoService.checkUser(name);
	  if(username==null||username.equals("")) {
		  model.addAttribute("msg", "用户不存在");
//		  session.setAttribute("msg","用户不存在");
//		  return "redirect:/loginPage"; 
		  return "login";
	  }
	  /*base64解密*/
	  BASE64Decoder decoder = new BASE64Decoder();
	  try {
	     byte[] key = decoder.decodeBuffer(password);
	     password = new String(key);
	  } catch (Exception e) {
		  model.addAttribute("msg", "密码错误!");
//		  session.setAttribute("msg","用户不存在");
//		  return "redirect:/loginPage"; 
		  return "login";
	  }
	  Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token=new UsernamePasswordToken(username,password,rememberMe); // 登录后存放进shiro token
      try {
		subject.login(token); 
//		Object ob = subject.getPrincipal();
		UserInfo userInfo=(UserInfo) subject.getPrincipal();
        //更新用户登录时间，也可以在ShiroRealm里面做
        session.setAttribute("userInfo", userInfo);
//        model.addAttribute("userInfo",userInfo);
//        model.addAttribute("name","吴万能");
//        return "index";
	   } catch (AuthenticationException e) {
		 //登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
	        String exception = (String) request.getAttribute("shiroLoginFailure");
	        model.addAttribute("msg","认证异常可能是您输入的密码错误！");
//	        session.setAttribute("msg", "认证异常可能是您输入的密码错误！");
	        //返回登录页面
//	        return "redirect:/loginPage";
	        return "login";
	   }
//      boolean hashRole = subject.hasRole("admin");
//      System.out.println(hashRole);
//       return "redirect:/index";
      return "index";
	}	
	 
	@RequestMapping(value="/loginPage")
	public String loginPage(){
		logger.info("loginPage begin");
		return "login";
	}	
	/*
	 * 没有权限的跳转页面
	 * */
	@RequestMapping(value="/403")
	public String Unauthorized(){
		logger.info("Unauthorized begin");
		return "403";
	}	
	@RequestMapping(value="/404")
	public String notFind(){
		logger.info("notFind begin");
		return "404";
	}	
	@RequestMapping(value="/index2")
	public String index2(){

		return "index2";
	}	
	/*
	 * 修改密码
	 * returen Map
	 * date 2019/03/23
	 * */ 
	@RequestMapping(value="/wjmm",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wjmiController(String username,String userpassword) {
		logger.info("wjmiController begin");
		Map< String,Object> mapCon = new HashMap<String, Object>();
		if(userpassword==null||userpassword.equals("")) {
			mapCon.put("xgmsg", "请输入密码");
		    return  mapCon;
		}
		Map< String,Object> mapSer = userInfoService.wjmm(username,userpassword);	
	   return  mapSer;
	}	
	/*
	 * 修改密码
	 * returen String
	 * date 2019/03/23
	 * */
	@RequestMapping(value="/register",method=RequestMethod.POST)
//	@ResponseBody
	public  String register(String zcname,String zcusername,String zcpassword,
			                String phone,String email,Integer age,String sex,Model model) {
		logger.info("register begin");
		//判断用户存不存在
	    UserInfo findUserInfo = userInfoService.findByusername(zcusername);
		if(findUserInfo!=null) {
			if(findUserInfo.getName().equals(zcname)||findUserInfo.getUsername().equals(zcusername)) {
				 model.addAttribute("msg", "用户已经存在!");
				  return "login";
			}
		}  
		
		//穿过来的数据与实体类映射
		UserInfo uf = new UserInfo();
		uf.setName(zcname);
		uf.setUsername(zcusername);
		uf.setSalt(zcusername);//盐值和uername 一样
		//密码解密再加密再与实体类中的属性做映射
		 /*base64解密*/
		 BASE64Decoder decoder = new BASE64Decoder();
		 try {
		     byte[] key = decoder.decodeBuffer(zcpassword);
		     zcpassword = new String(key);
		  } catch (Exception e) {
			  e.printStackTrace();
			  model.addAttribute("msg", "注册出错!");
			  return "login";
		  }
		 //加密
		 Md5Hash md5 = new  Md5Hash(zcpassword,zcusername,1);
		 uf.setPassword(md5.toString());
		 uf.setPhone(phone);
		 uf.setEmail(email);
		 uf.setAge(age);
		 uf.setSex(sex);
		//调用service保存数据
		 UserInfo userInfo;
		try {
			userInfo = userInfoService.save(uf);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "用户已经存在!");
		   return "login";
		}
		//返回数据和视图
		 if(userInfo==null) {
			 model.addAttribute("msg", "注册出错!");
			 return "login"; 
		 }else {
			 model.addAttribute("msg", "注册成功!");
			 return "login"; 
		 }
	}
//	  public static void main(String[] arg) {
//		  String salt = "admin";
//		  Md5Hash md5 = new  Md5Hash("123456",salt,1);
//		  System.out.println(md5);
//		  Depart2 depart = new  Depart2();
//		  depart.g
//	  }
}







