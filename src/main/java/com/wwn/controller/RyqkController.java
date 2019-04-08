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

import com.wwn.jpa.entity.Ryqk;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.RyqkService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/ryqk")
public class RyqkController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private RyqkService ryqkService;
	 
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
	    
		logger.info("ryqkController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Ryqk ryqkFrmData = new Ryqk();
		Page<Ryqk> ryqk;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String rqcxxm = (String) gjcxMap.get("rqcxxm");//人员情况参项人姓名
	    String rqbmmc = (String) gjcxMap.get("rqbmmc");//人员情况部门名称
	    String rqxm = (String) gjcxMap.get("rqxm");//人员情况项目名称
	    String rqjslb = (String) gjcxMap.get("rqjslb");//记录类别
	    String rqksrq = (String) gjcxMap.get("rqksrq");//开始时间
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(rqksrq!=null&&!rqksrq.equals(""))
              date =  simpleDateFormat.parse(rqksrq);
          ryqkFrmData.setRqksrq(date);
          System.out.println(date);
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    ryqkFrmData.setRqcxxm(rqcxxm);
	    ryqkFrmData.setRqbmmc(rqbmmc);
	    ryqkFrmData.setRqxm(rqxm);
	    ryqkFrmData.setRqjslb(rqjslb);
	    
		try {
			ryqk = ryqkService.find(page, size, ryqkFrmData);
			map.put("total", ryqk.getTotalElements());
			map.put("rows", ryqk.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 人员情况
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	   
		logger.info("ryqkController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		
        String ry_idList =  req.getParameter("idList"); 
		String rqzprxm =  req.getParameter("name");
		String name = uf.getName();
		if(ry_idList==null||rqzprxm==null) {
			logger.info("请输入rq_id号和发布人");
			map.put("msg", "请输入rq_id号和发布人");
			return map;
		}
		if(!name.equals(rqzprxm)) {
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
			delete = ryqkService.delete(wtidIntArr,rqzprxm);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新人员情况
	 * data 2019/04/03
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Ryqk ryqk ,HttpServletRequest req  ) {
		
		logger.info("ryqkController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String rqzprxm = ryqk.getRqzprxm();
	    if(!name.equals(rqzprxm)) {
			logger.info("发布人应选择当前登录人");
			map.put("ryqkmsg", "发布人应选择当前登录人");
			return map;
		}
	    Integer rq_id = ryqk.getRq_id();
		if(rq_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			ryqk.setRq_id(rq_id);
	    map = ryqkService.save(ryqk);
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
    
     
}
