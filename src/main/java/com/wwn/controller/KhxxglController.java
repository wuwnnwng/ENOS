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

import com.wwn.jpa.entity.Khxxgl;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.KhxxglService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/khxxgl")
public class KhxxglController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private KhxxglService khxxglService; 
	 
	/*
	 * 分页加模糊查询
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("khxxglController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Khxxgl khxxglFrmData = new Khxxgl();
		Page<Khxxgl> khxxgl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String khname = (String) gjcxMap.get("khname");//客户姓名
	    String khhzzt = (String) gjcxMap.get("khhzzt");//合作转态 ：进行中，已解除
	    String khhzkssq = (String) gjcxMap.get("khhzkssq");//与公司合作开始日期
	    String khhzjsrq = (String) gjcxMap.get("khhzjsrq");//与公司合作 结束日期
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(khhzkssq!=null&&!khhzkssq.equals("")) {
            date =  simpleDateFormat.parse(khhzkssq);
            khxxglFrmData.setKhhzkssq(date);
            date = null;
      	}
        if(khhzjsrq!=null&&!khhzjsrq.equals(""))
              date =  simpleDateFormat.parse(khhzjsrq);
          khxxglFrmData.setKhhzjsrq(date);
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    khxxglFrmData.setKhname(khname);
	    khxxglFrmData.setKhhzzt(khhzzt);
	   
	    
		try {
			khxxgl = khxxglService.find(page, size, khxxglFrmData);
			map.put("total", khxxgl.getTotalElements());
			map.put("rows", khxxgl.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete客户管理
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	  
		logger.info("khxxglController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String  kh_idList =  req.getParameter("idList"); 
		String khcjr =  req.getParameter("name");
		String name = uf.getName();
		if( kh_idList==null||khcjr==null) {
			logger.info("请输入 kh_id号和创建人");
			map.put("msg", "请输入 kh_id号和创建人");
			return map;
		}
		if(!name.equals(khcjr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =  kh_idList.substring(1,  kh_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = khxxglService.delete(wtidIntArr,khcjr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新客户管理
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Khxxgl khxxgl ,HttpServletRequest req  ) {
		
		logger.info("khxxglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String khcjr = khxxgl.getKhcjr();
	    if(!name.equals(khcjr)) {
			logger.info("创建人应选择当前登录人");
			map.put("khxxglmsg", "创建人应选择当前登录人");
			return map;
		}
	    Integer  kh_id = khxxgl.getKh_id();
		if(kh_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			khxxgl.setKh_id(kh_id);
	    map = khxxglService.save(khxxgl);
		return map; 
	}
	 
	
	 
	/*
	 * 日期转换
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
     
}
