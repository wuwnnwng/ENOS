package com.wwn.controller;
 
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

import com.alibaba.fastjson.JSON;
import com.wwn.jpa.dao.DepartRepository;
import com.wwn.jpa.entity.Depart;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Wdtx;
import com.wwn.service.WdtxService;

@RestController
@RequestMapping("/wdtx")
public class WdtxController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private WdtxService wdtxService;
	@Autowired   
	private DepartRepository departRepository;
	@RequestMapping("/find")
	public Map<String ,Object> find(Integer page ,Integer size,String field,String dlzh) {
	    logger.info("WdtxController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Page<Wdtx> wdtx = wdtxService.find(page, size, field,dlzh);
		map.put("total", wdtx.getTotalElements());
		map.put("rows", wdtx.getContent());
		return map;
	}
	 
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	    logger.info("WdtxController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String wtidList =  req.getParameter("wt_idList");
		String fbr =  req.getParameter("fbr");
		String name = uf.getName();
		if(wtidList==null||fbr==null) {
			logger.info("请输入wt_id号和发布人");
			map.put("wdtxmsg", "请输入wt_id号和发布人");
//			return JSON.toJSONString("success");
			return map;
		}
		if(!name.equals(fbr)) {
			logger.info("只能删除你自己发布的提醒");
			map.put("wdtxmsg", "只能删除你自己发布的提醒");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = wtidList.substring(1, wtidList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete = wdtxService.delete(wtidIntArr,fbr);
		map.put("wdtxmsg", "已删除"+delete+"数据");
		return map;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Wdtx wdtx ,HttpServletRequest req) {
		
		logger.info("WdtxController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String fbr = wdtx.getFbr();
	    if(!name.equals(fbr)) {
			logger.info("发布人应选择当前登录人");
			map.put("wdtxmsg", "发布人应选择当前登录人");
			return map;
		}
		Integer wt_id = wdtx.getWt_id();
		if(wt_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			wdtx.setWt_id(wt_id);
	    map = wdtxService.save(wdtx);
		return map; 
	}
	/*
	 * 修改确认转态
	 * return String
	 * date 2019/03/26
	 * */
	@RequestMapping(value = "/qrstatus", method = RequestMethod.POST)
	public Map<String ,Object> qrstatus( HttpServletRequest req ) {
		logger.info("WdtxController ->savePerson begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    String wtidList =  req.getParameter("wt_id");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = wtidList.substring(1, wtidList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
	    if(wtidIntArr==null ) {
			logger.info("请输入wt_id号和发布人");
			map.put("wdtxmsg", "请输入wt_id号和发布人");
			return map;
		}
		Integer rows = null;
		if(wtidIntArr!=null)
		     rows = wdtxService.qrstatus(wtidIntArr);
		map.put("rows", rows);
		return map;
	}
	/*
	 * 得到员工新姓名
	 * Map<String ,Object> 
	 * date 2019/03/27
	 * */
	@RequestMapping(value = "/getUserName", method = RequestMethod.POST)
	public String getUserName() {
		logger.info("WdtxController ->getUserName begin");
//	    Map< String,Object> map = new HashMap<String, Object>();
	    List<UserInfo> ufList = new ArrayList<UserInfo>();
	    List<String> List = new ArrayList<String>();
	    List = wdtxService.getUserName();
	    for (String str : List) {
	    	UserInfo uf = new UserInfo();
	    	uf.setName(str);
	    	ufList.add(uf);
		}
	    return JSON.toJSONString(ufList);
	}
	/*
	 * 得到部门 
	 * Map<String ,Object> 
	 * date 2019/03/27
	 * */
	@RequestMapping(value = "/getDepart", method = RequestMethod.POST)
	public String getDepart() {
		logger.info("WdtxController ->getDepart begin");
	    List<Depart> ufList = new ArrayList<Depart>();
	    ufList = departRepository.getDepart();
	    return JSON.toJSONString(ufList);
	} 
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
