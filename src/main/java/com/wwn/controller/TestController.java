package com.wwn.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userInfo")
public class TestController {

//	@RequiresRoles("admin1")
	@RequestMapping("/userList")
	public String userList() {
		return "index";
	}
//	@RequiresRoles("admin")
	@RequestMapping("/userAdd")
	public String userAdd() {
		return "index";
	}
}
