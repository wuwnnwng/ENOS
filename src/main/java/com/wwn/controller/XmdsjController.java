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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Xmdsj;
import com.wwn.service.XmdsjService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/xmdsj")
public class XmdsjController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private XmdsjService xmdsjService;
	 
	/*
	 * 分页加模糊查询
	 * data 2019/04/02
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("xmdsjController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Xmdsj xmdsjFrmData = new Xmdsj();
		Page<Xmdsj> xmdsj;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String xdxmmc = (String) gjcxMap.get("xdxmmc");//项目大事记项目名称
	    String xdjlr = (String) gjcxMap.get("xdjlr");//项目大事记记录人
	    String xdshr = (String) gjcxMap.get("xdshr");//项目大事记审核人
	    String xdshzt = (String) gjcxMap.get("xdshzt");//项目大事记审核状态
	    String xdrq = (String) gjcxMap.get("xdrq");//项目大事日期
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(xdrq!=null&&!xdrq.equals(""))
              date =  simpleDateFormat.parse(xdrq);
          xmdsjFrmData.setXdrq(date);
          System.out.println(date);
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    xmdsjFrmData.setXdxmmc(xdxmmc);
	    xmdsjFrmData.setXdjlr(xdjlr);
	    xmdsjFrmData.setXdshr(xdshr);
	    xmdsjFrmData.setXdshzt(xdshzt);
	    
		try {
			xmdsj = xmdsjService.find(page, size, xmdsjFrmData);
			map.put("total", xmdsj.getTotalElements());
			map.put("rows", xmdsj.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 项目大事记
	 * data 2019/04/02
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	    logger.info("xmdsjController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String xd_idList =  req.getParameter("idList"); 
		String xdjlr =  req.getParameter("name");
		String name = uf.getName();
		if(xd_idList==null||xdjlr==null) {
			logger.info("请输入xd_id号和发布人");
			map.put("msg", "请输入xd_id号和发布人");
			return map;
		}
		if(!name.equals(xdjlr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = xd_idList.substring(1, xd_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = xmdsjService.delete(wtidIntArr,xdjlr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新项目大事记
	 * data 2019/04/02
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Xmdsj xmdsj ,HttpServletRequest req  ) {
		
		logger.info("xmdsjController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String xdjlr = xmdsj.getXdjlr();
	    if(!name.equals(xdjlr)) {
			logger.info("发布人应选择当前登录人");
			map.put("xmdsjmsg", "发布人应选择当前登录人");
			return map;
		}
	    Integer xd_id = xmdsj.getXd_id();
		if(xd_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			xmdsj.setXd_id(xd_id);
	    map = xmdsjService.save(xmdsj);
		return map; 
	}
	/*
	 * 审核项目大事记
	 * data 2019/04/02
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/shxmdsj", method = RequestMethod.POST)
	public Map<String,Object> shxmdsj(Xmdsj xmdsj ,HttpServletRequest req  ) {
		
		logger.info("xmdsjController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String permission = uf.getRoleList().get(0).getPermissions().get(0).getPermission();
	    if(!permission.equals("all")) {
	    	logger.info("操作人没有权限");
			map.put("xmdsjmsg", "操作人没有权限");
			return map;
		}
	    String xdshzt = xmdsj.getXdshzt();
	    if(xdshzt.equals("未审核")) {
	    	xmdsj.setXdshzt("已审核");
	    }
	    Integer xd_id = xmdsj.getXd_id();
		if(xd_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			xmdsj.setXd_id(xd_id);
	    map = xmdsjService.save(xmdsj);
		return map; 
	}
	/*
	 * 修改审核转态
	 * return String
	 * date 2019/04/02 
	 * */
	@RequestMapping(value = "/qrstatus", method = RequestMethod.POST)
	public Map<String ,Object> qrstatus( HttpServletRequest req ) {
		
		logger.info("gzlyController ->savePerson begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    String xd_id_list =  req.getParameter("qr_id_list");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = xd_id_list.substring(1, xd_id_list.length()-1).split(",");
		
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer rows = 0;
		if(wtidIntArr!=null)
		     rows = xmdsjService.qrstatus(wtidIntArr);
		map.put("rows", rows);
		return map;
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
