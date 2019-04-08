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
import com.wwn.jpa.entity.Wcgl;
import com.wwn.service.WcglService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/wcgl")
public class WcglController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private WcglService wcglService;
	 
	/*
	 * 分页加模糊查询
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("wcglController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Wcgl wcglFrmData = new Wcgl();
		Page<Wcgl> wcgl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String wcr = (String) gjcxMap.get("wcr");//外出管理项目名称
	    String wcrbmmc = (String) gjcxMap.get("wcrbmmc");//外出管理 人
	    String wcglxm = (String) gjcxMap.get("wcglxm");//外出关联项目
	    String wcshzt = (String) gjcxMap.get("wcshzt");//外出管理审核状态
	    String wcksrq = (String) gjcxMap.get("wcksrq");//项目大事开始日期
	    String wcjsrq = (String) gjcxMap.get("wcjsrq");//项目大事结束日期
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(wcksrq!=null&&!wcksrq.equals("")) {
            date =  simpleDateFormat.parse(wcksrq);
            wcglFrmData.setWcksrq(date);
            date = null;
      	}
        if(wcjsrq!=null&&!wcjsrq.equals(""))
              date =  simpleDateFormat.parse(wcjsrq);
          wcglFrmData.setWcjsrq(date);  
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    wcglFrmData.setWcr(wcr);
	    wcglFrmData.setWcrbmmc(wcrbmmc);
	    wcglFrmData.setWcglxm(wcglxm);
	    wcglFrmData.setWcshzt(wcshzt);
	    
		try {
			wcgl = wcglService.find(page, size, wcglFrmData);
			map.put("total", wcgl.getTotalElements());
			map.put("rows", wcgl.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 外出管理
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	  
		logger.info("wcglController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String  wc_idList =  req.getParameter("idList"); 
		String wcr =  req.getParameter("name");
		String name = uf.getName();
		if( wc_idList==null||wcr==null) {
			logger.info("请输入 wc_id号和外出人");
			map.put("msg", "请输入 wc_id号和外出人");
			return map;
		}
		if(!name.equals(wcr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =  wc_idList.substring(1,  wc_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = wcglService.delete(wtidIntArr,wcr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新外出管理
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Wcgl wcgl ,HttpServletRequest req  ) {
		
		logger.info("wcglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String wcr = wcgl.getWcr();
	    if(!name.equals(wcr)) {
			logger.info("外出人应选择当前登录人");
			map.put("wcglmsg", "外出人应选择当前登录人");
			return map;
		}
	    Integer  wc_id = wcgl.getWc_id();
		if( wc_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			wcgl.setWc_id(wc_id);
	    map = wcglService.save(wcgl);
		return map; 
	}
	 
	/*
	 * 审核外出管理
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/shwcgl", method = RequestMethod.POST)
	public Map<String,Object> shwcgl(Wcgl wcgl ,HttpServletRequest req  ) {
		
		logger.info("wcglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String permission = uf.getRoleList().get(0).getPermissions().get(0).getPermission();
	    if(!permission.equals("all")) {
	    	logger.info("操作人没有权限");
			map.put("xmdsjmsg", "操作人没有权限");
			return map;
		}
	    String xdshzt = wcgl.getWcshzt();
	    if(xdshzt.equals("未审核")) {
	    	wcgl.setWcshzt("已审核");
	    }
	    Integer  wc_id = wcgl.getWc_id();
		if( wc_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			wcgl.setWc_id(wc_id);
	    map = wcglService.save(wcgl);
		return map; 
	}
	 
	/*
	 * 修改审核转态
	 * return String
	 * date 2019/04/04 
	 * */
	@RequestMapping(value = "/qrstatus", method = RequestMethod.POST)
	public Map<String ,Object> qrstatus( HttpServletRequest req ) {
		
		logger.info("gzlyController ->savePerson begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    String  wc_id_list =  req.getParameter("qr_id_list");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =  wc_id_list.substring(1,  wc_id_list.length()-1).split(",");
		
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer rows = 0;
		if(wtidIntArr!=null)
		     rows = wcglService.qrstatus(wtidIntArr);
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
