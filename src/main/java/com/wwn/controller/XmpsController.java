package com.wwn.controller;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Xmps;
import com.wwn.service.XmpsService;
import com.wwn.util.PJCommon;
 
@RestController
@RequestMapping("/xmps")
public class XmpsController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private XmpsService xmpsService;
	 
	/*
	 * 分页加模糊查询
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("xmpsController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Xmps xmpsFrmData = new Xmps();
		Page<Xmps> xmps;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String xpxmmc = (String) gjcxMap.get("xpxmmc");//项目评审 项目名称
	    String xpr = (String) gjcxMap.get("xpr");//项目评审人
	    String xprq = (String) gjcxMap.get("xprq");//项目评审 日期
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(xprq!=null&&!xprq.equals(""))
              date =  simpleDateFormat.parse(xprq);
          xmpsFrmData.setXprq(date);
          System.out.println(date);
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    xmpsFrmData.setXpxmmc(xpxmmc);
	    xmpsFrmData.setXpr(xpr);
	    
		try {
			xmps = xmpsService.find(page, size, xmpsFrmData);
			map.put("total", xmps.getTotalElements());
			map.put("rows", xmps.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 项目评审
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	   
		logger.info("xmpsController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		
        String ry_idList =  req.getParameter("idList"); 
		String xpr =  req.getParameter("name");
		String name = uf.getName();
		if(ry_idList==null||xpr==null) {
			logger.info("请输入xp_id号和发布人");
			map.put("msg", "请输入xp_id号和发布人");
			return map;
		}
		if(!name.equals(xpr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = ry_idList.substring(1, ry_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = xmpsService.delete(wtidIntArr,xpr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新项目评审
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Xmps xmps ,HttpServletRequest req  ) {
		
		logger.info("xmpsController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String xpr = xmps.getXpr();
	    if(!name.equals(xpr)) {
			logger.info("发布人应选择当前登录人");
			map.put("xmpsmsg", "发布人应选择当前登录人");
			return map;
		}
	    Integer xp_id = xmps.getXp_id();
		if(xp_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			xmps.setXp_id(xp_id);
	    map = xmpsService.save(xmps);
		return map; 
	}
	 
 
	/*
	 * 日期转换
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
//    public static void  main(String[] arg) {
//    	
//    	List list = new  ArrayList<>();
//    	List list2 = new  ArrayList<>();
////    	list.add(1);
////     	list.add(2);
////     	list.add(3);
//     	int a = 0;
//     	Thread th1 = new Thread() {
//     	   
//     		@Override
//     		public void run() {
////     			 try {
////				
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
//     			for(int i = 0;i<100;i++) {
//			     	a++;
//			     	System.out.println("th1:+"+a);
////					Thread.sleep(1000);
//				 }
//     		}
//    
//     	};
//     	Thread th2 = new Thread() {
//     		@Override
//     		public void run() {
//     			 
////     			try {
////					Thread.sleep(2000);
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
//     			for(int i = 0;i<100;i++) {
//     				a--;
//    		     	System.out.println("th2:-"+a);
//				 }
//     			
//     		}
//     	};
//     	
//     	th1.start();
//     	th2.start();
////     	list.set(3, 4);
////     	System.out.println(list);
////     	
//    	List link = new  LinkedList<>();
//     	List vect = new  Vector<>();
//     	
//     	Set set = new HashSet<>();
//     	set.add(1);
//     	set.add(2);
//     	set.add(3);
//     	set.add(4);
//     	set.add(5);
//     	set.add(6);
////     	System.out.println(set);
//     	Map map = new HashMap<String, Object>();
//     	map.put("1", 1);
//    	map.put("2", 1);
//    	map.put("1", 1);
////    	System.out.println(map);
//     	Map tableM = new Hashtable<>();
//    	Map treeM = new TreeMap<>();
//    	
//     
//    }
     
}
