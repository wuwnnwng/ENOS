package com.wwn.controller;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wwn.jpa.entity.TPerson;
import com.wwn.service.TPersonService;

@RestController
@RequestMapping("/person")
public class TPersonController {
	
	@Autowired
	private TPersonService tPersonService;
//	@RequiresRoles("admin")
	@RequestMapping("/findPersonByPage")
	public Map<String ,Object> findPersonByPage(Integer page ,Integer size) {
		Map< String,Object> map = new HashMap<String, Object>();
		Page<TPerson> tPerson = tPersonService.find(page, size);
		map.put("total", tPerson.getTotalElements());
		map.put("rows", tPerson.getContent());
		map.put("totalPages", tPerson.getTotalPages());
		map.put("first", tPerson.isFirst()); 
		map.put("last", tPerson.isLast());
		map.put("numberOfElements", tPerson.getNumberOfElements());
		map.put("size",tPerson.getSize());
		return map;
	}
	 
	@RequestMapping(value = "/deletePerson", method = RequestMethod.POST)
	public String deletePerson(HttpServletRequest req) {
		String pidList =  req.getParameter("pidList");
		List<Integer> pidIntArr  = new ArrayList<Integer>();
		String[] pidStrArr = pidList.substring(1, pidList.length()-1).split(",");
		for (int i = 0; i < pidStrArr.length; i++) {
			pidIntArr.add(Integer.parseInt(pidStrArr[i]));
		}
		tPersonService.deletePerson(pidIntArr);
		return JSON.toJSONString("success");
//		return "success";
	}
	@RequestMapping(value = "/savePerson", method = RequestMethod.POST)
	public Map<String,Object> savePerson(TPerson tPerson ) {
		Map<String, Object> map = new HashMap<>();
//		String pid =  req.getParameter("pid");
		Integer pid = tPerson.getPid();
//		try {
//			pid = Integer.parseInt(pid);
//		} catch (NumberFormatException e) {
//			map.put("error", "查询数据出错了请联系管理员!");
//			e.printStackTrace();
//			return map;
//		}
		if(pid!=null&&pid.equals(""))
			tPerson.setPid(pid);
	    map = tPersonService.save(tPerson);
		return map; 
	}
	@RequestMapping(value = "/searchPerson", method = RequestMethod.POST)
	public Map<String, Object> searchPerson(String field,Integer page ,Integer size) {
		Map<String, Object> map = new HashMap<>();
		Integer count = tPersonService.getPersonCount(field,page,size);
		List<TPerson> list = new ArrayList<TPerson>();
		list = tPersonService.searchPerson(field, page , size);
		map.put("total", count);
		map.put("rows", list);
		map.put("numberOfElements", size);
//		return JSON.toJSONString(map); 
		return map; 
	}
//	public static void main (String[] arg) {
//		Map< Object,Object> map = new HashMap<Object, Object>();
//		map.put(null, null);
//		System.out.println(map.get(null));
//		Map< Object,Object> map1 = new Hashtable<>();
//		map1.put(null, null);
//		System.out.println(map1.get(null));
//	}
}
