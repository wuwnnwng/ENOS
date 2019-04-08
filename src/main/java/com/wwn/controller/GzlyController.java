package com.wwn.controller;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wwn.jpa.entity.Gzly;
import com.wwn.jpa.entity.Hyjl;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.GzlyService;
import com.wwn.util.PJCommon;
 

@RestController
@RequestMapping("/gzly")
public class GzlyController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private GzlyService gzlyService;
 
	@RequestMapping("/find")
	public Map<String ,Object> find(Integer page ,Integer size ,String dlzh,HttpServletRequest  request) {
	   
		logger.info("gzlyController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		
		Gzly gzlyFrmData = new Gzly();
		Page<Gzly> gzly;
		
	    Map glcxMap = PJCommon.getRequestParamMap(request);
	    String fjrq = (String) glcxMap.get("fjrqcx");//发件日期
	    String fjr = (String) glcxMap.get("fjrcx");//发件人
	    String lystatus = (String) glcxMap.get("lystatuscx");//留言转态

	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
        	Date date = null;
        	if(fjrq!=null&&!fjrq.equals(""))
                date =  simpleDateFormat.parse(fjrq);
        	gzlyFrmData.setFjrq(date);
        } catch(ParseException px) {
            px.printStackTrace();
            map.put("total", 0);
			map.put("rows", null);
			return map;
        }
	    gzlyFrmData.setFjr(fjr);
	    gzlyFrmData.setLystatus(lystatus);
	    
//	    String name = gzlyFrmData.getLystatus();
		
		gzly = gzlyService.find(page, size,gzlyFrmData,dlzh);
		map.put("total", gzly.getTotalElements());
		map.put("rows", gzly.getContent());
		return map;
	}
	 
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	
		logger.info("gzlyController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
        
		String gl_idList =  req.getParameter("idList");
		String fjr =  req.getParameter("name");
		String name = uf.getName();
		
		if(gl_idList==null||fjr==null) {
			logger.info("请输入gl_id号和发布人");
			map.put("msg", "请输入gl_id号和发布人");
			return map;
		}
		if(!name.equals(fjr)) {
			logger.info("只能删除你自己发布的留言");
			map.put("msg", "只能删除你自己发布的留言");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = gl_idList.substring(1, gl_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete = gzlyService.delete(wtidIntArr,fjr);
		map.put("msg", "已删除"+delete+"数据");
		return map;
	}
	
	/*save*/
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Gzly gzly ,HttpServletRequest req) {
		
		logger.info("gzlyController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
		 /*判断发件人和收件人是否是同一个人，如果是直接返回提示信息*/
		if(gzly.getFjr().equals(gzly.getSjr())) {
			logger.info("发件人和收件人不能是同一个人");
			map.put("gzlymsg", "发件人和收件人不能是同一个人");
			return map;
		}
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    
	    String name = uf.getName();
	    String fjr = gzly.getFjr();
	    
	    if(!name.equals(fjr)) {
			logger.info("发件人应选择当前登录人");
			map.put("gzlymsg", "发件人应选择当前登录人");
			return map;
		}
	    String status = gzly.getLystatus();
	    if(status == null) {
	    	gzly.setLystatus("未确认");
	    }
		Integer gl_id = gzly.getGl_id();
		if(gl_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			gzly.setGl_id(gl_id);
	    map = gzlyService.save(gzly);
		return map; 
	}
	/*
	 * 修改确认转态
	 * return String
	 * date 2019/03/26
	 * */
	@RequestMapping(value = "/qrstatus", method = RequestMethod.POST)
	public Map<String ,Object> qrstatus( HttpServletRequest req ) {
		
		logger.info("gzlyController ->savePerson begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    String gl_id_list =  req.getParameter("qr_id_list");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = gl_id_list.substring(1, gl_id_list.length()-1).split(",");
		
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
//	    if(wtidIntArr==null ) {
//			logger.info("请输入gl_id号和发布人");
//			map.put("gzlymsg", "请输入gl_id号和发布人");
//			return map;
//		}
		Integer rows = 0;
		if(wtidIntArr!=null)
		     rows = gzlyService.qrstatus(wtidIntArr);
		map.put("rows", rows);
		return map;
	}
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
