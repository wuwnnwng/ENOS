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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wwn.jpa.dao.SbdkRepository;
import com.wwn.jpa.entity.Sbdk;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.util.PJCommon;
 

@RestController
@RequestMapping("/sbdk")
public class SbdkController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private SbdkRepository sbdkrepository;
 
	@RequestMapping(value="/find", method = RequestMethod.POST)
	public Map<String ,Object> find(String dlzh,HttpServletRequest  request) {
	   
		logger.info("sbdkController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Map< String,Object> retMap = new HashMap<String, Object>();
		List<Map< String,Object>> result = new ArrayList<Map< String,Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E a HH点mm分");
//	  　     System.out.println(f.format(now));
//	    f=new SimpleDateFormat("a hh点mm分ss秒");
		
		List<Sbdk> sbdk = new ArrayList<Sbdk>();
		sbdk = sbdkrepository.find(dlzh);
		
		for (Sbdk sd : sbdk) {
			retMap.put("dk_id", sd.getDk_id());
			retMap.put("dk_name", sd.getDk_name());
			retMap.put("dk_depart", sd.getDk_depart());
			retMap.put("dk_kssj", sdf.format(sd.getDk_kssj()));
			Date dk_jssj = sd.getDk_jssj();
			if(dk_jssj!=null&&!dk_jssj.equals("")) {
				retMap.put("dk_jssj", sdf.format(dk_jssj));
			} 
		}
		result.add( retMap);
		sbdk = null;
		retMap = null;
		map.put("total", result.size());
		map.put("rows", result);
		return map;
	}
	 
	 
	
	/*
	 * 上班打卡
	 * data 2019/04/05
	 * 
	 * wwn
	 * */
	@RequestMapping(value = "/newsbdk", method = RequestMethod.POST)
	public Map<String,Object> newsbdk( HttpServletRequest req) {
		
		logger.info("sbdkController ->newsbdk begin");
		Map<String, Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    
	    /*判断浸提是不是已经打卡，如果已经打卡就提示不要重复操作*/
	    String dlzh = uf.getName();
	    List<Sbdk> newSbdk;
	    try {
			newSbdk = sbdkrepository.find(dlzh);
			if(newSbdk.size()>0) {
				map.put("sbdkmsg", "今日已打上班卡,请勿重操作!");
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("sbdkmsg", "打卡出错，请稍后重试!");
			return map;
		}
	   
	    /*如果今日没有打卡就可以新建打卡记录*/
	    Sbdk saveSbdk = new Sbdk() ;
	    saveSbdk.setDk_name(dlzh);
	    String departName = uf.getDepart().getD_name();
	    saveSbdk.setDk_depart(departName);
	    saveSbdk.setDk_kssj(new Date());
	    
		try {
			saveSbdk =  sbdkrepository.save(saveSbdk);
			map.put("saveSbdk", saveSbdk);
			map.put("sbdkmsg", "打卡记录保存成功");
		} catch (Exception e) {
			map.put("sbdkmsg", "打卡记录保存失败");
			e.printStackTrace();
		}
		return map; 
	}
   
	/*
	 * 下班打卡
	 * data 2019/04/05
	 * 
	 * wwn
	 * */
	@RequestMapping(value = "/editsbdk", method = RequestMethod.POST)
	public Map<String,Object> editsbdk( HttpServletRequest req) {
		
		logger.info("sbdkController ->editsbdk begin");
		Map<String, Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    
	    /*判断今日是否已经打卡，如果没有打卡提示今日上班卡还没有打*/
	    String dlzh = uf.getName();
	    List<Sbdk> editSbdk;
	    try {
	    	editSbdk = sbdkrepository.find(dlzh);
			if(editSbdk.size()==0) {
				map.put("sbdkmsg", "今日没打上班卡，请先打上班卡!");
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("sbdkmsg", "打卡出错，请稍后重试!");
			return map;
		}
	   
	    /*如果今日已经打卡就可以修改打卡记录*/
	    Sbdk saveSbdk = editSbdk.get(0);
	    /*判断是否已经打下班卡*/
	    if(saveSbdk.getDk_jssj()!=null) {
	    	map.put("sbdkmsg", "今日已打下班卡,请勿重操作!");
			return map;
	    }
	    saveSbdk.setDk_jssj(new Date());
	    
		try {
			saveSbdk =  sbdkrepository.save(saveSbdk);
			map.put("saveSbdk", saveSbdk);
			map.put("sbdkmsg", "打卡记录保存成功");
		} catch (Exception e) {
			map.put("sbdkmsg", "打卡记录保存失败");
			e.printStackTrace();
		}
		return map; 
	}
	
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
//    public static void main(String[] args) {
//    	Date now=new Date();
//    	SimpleDateFormat f= new SimpleDateFormat("今天是"+"yyyy年MM月dd日 E a kk点mm分");
//       System.out.println(f.format(now));
//
//    	f=new SimpleDateFormat("a hh点mm分ss秒");
//    	System.out.println(f.format(now));
//	}
}
