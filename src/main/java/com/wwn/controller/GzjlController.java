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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wwn.jpa.dao.XmRepository;
import com.wwn.jpa.entity.Gzjl;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Xm;
import com.wwn.service.GzjlService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/gzjl")
public class GzjlController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private GzjlService gzjlService;
	@Autowired  
	private XmRepository xmrepository;
	 
	/*
	 * 分页加模糊查询
	 * data 2019/03/30
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("GzjlController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Gzjl gzjlFrmData = new Gzjl();
		Page<Gzjl> gzjl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String xmmc = (String) gjcxMap.get("xmmc");//项目名称
	    String jlrxm = (String) gjcxMap.get("jlrxm");//姓名
	    String gznr = (String) gjcxMap.get("gznr");//工作内容
	    String bmmc = (String) gjcxMap.get("bmmc");//部门
//	    String pageFormData = (String) gjcxMap.get("page");//页码
//	    String sizeFormData = (String) gjcxMap.get("size");//页面大小
//	     
//	    if(pageFormData!=null&&!pageFormData.equals("")) {
//	    	page = Integer.parseInt(pageFormData);
//	    }
//	    if(sizeFormData!=null&&!sizeFormData.equals("")) {
//	    	size = Integer.parseInt(sizeFormData);
//	    }
	    gzjlFrmData.setBmmc(bmmc);
	    gzjlFrmData.setXmmc(xmmc);
	    gzjlFrmData.setGznr(gznr);
	    gzjlFrmData.setJlrxm(jlrxm);
	    
		try {
			gzjl = gzjlService.find(page, size, gzjlFrmData);
			map.put("total", gzjl.getTotalElements());
			map.put("rows", gzjl.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 工作记录
	 * data 2019/03/30
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> deleteXxfb(HttpServletRequest req) {
	    logger.info("GzjlController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String gj_idList =  req.getParameter("idList"); 
		String jlrxm =  req.getParameter("name");
		String name = uf.getName();
		if(gj_idList==null||jlrxm==null) {
			logger.info("请输入gj_id号和发布人");
			map.put("msg", "请输入gj_id号和发布人");
			return map;
		}
		if(!name.equals(jlrxm)) {
			logger.info("只能删除你自己的工作记录");
			map.put("msg", "只能删除你自己的工作记");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = gj_idList.substring(1, gj_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = gzjlService.delete(wtidIntArr,jlrxm);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	
	
	
	/*
	 * 保存或者更新信工作记录
	 * data 2019/03/30
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Gzjl gzjl ,HttpServletRequest req  ) {
		
		logger.info("GzjlController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String jlrxm = gzjl.getJlrxm();
	    if(!name.equals(jlrxm)) {
			logger.info("发布人应选择当前登录人");
			map.put("gzjlmsg", "发布人应选择当前登录人");
			return map;
		}
	    Integer gj_id = gzjl.getGj_id();
		if(gj_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			gzjl.setGj_id(gj_id);
	    map = gzjlService.save(gzjl);
		return map; 
	}
	 
	/*
	 * 审核工作记录
	 * data 2019/03/30
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/shenhe", method = RequestMethod.POST)
	public Map<String,Object> shenhe(Gzjl gzjl ,HttpServletRequest req  ) {
		
		logger.info("GzjlController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String permission = uf.getRoleList().get(0).getPermissions().get(0).getPermission();
	    if(!permission.equals("all")) {
	    	logger.info("操作人没有权限");
			map.put("gzjlmsg", "操作人没有权限");
			return map;
		}
	    Integer gj_id = gzjl.getGj_id();
		if(gj_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			gzjl.setGj_id(gj_id);
	    map = gzjlService.save(gzjl);
		return map; 
	}
	 
	/*
	 * 获取项目名称
	 * data 2019/03/30
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping("/getXm")
	public String getXm() {
		logger.info("GzjlController ->getXm begin");
	    List<Xm> xmList = new ArrayList<Xm>();
	    xmList = xmrepository.getXm();
	    return JSON.toJSONString(xmList);
	}
	 
	/*
	 * 日期转换
	 * data 2019/03/30
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
    
   
   
}
