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

import com.alibaba.fastjson.JSON;
import com.wwn.jpa.dao.QjlxRepository;
import com.wwn.jpa.entity.Qjgl;
import com.wwn.jpa.entity.Qjlx;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Xm;
import com.wwn.service.QjglService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/qjgl")
public class QjglController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private QjglService qjglService; 
	@Autowired  
	private QjlxRepository qjlxrepository;
	 
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
	    
		logger.info("qjglController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Qjgl qjglFrmData = new Qjgl();
		Page<Qjgl> qjgl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String qjr = (String) gjcxMap.get("qjr");//请假管理 人
	    String qjrbmmc = (String) gjcxMap.get("qjrbmmc");//请假管理项目名称
	    String qjlx = (String) gjcxMap.get("qjlx");//请假类型
	    String qjshzt = (String) gjcxMap.get("qjshzt");//请假管理审核状态
	    String qjksrq = (String) gjcxMap.get("qjksrq");//请假管理开始日期
	    String qjjsrq = (String) gjcxMap.get("qjjsrq");//请假管理结束日期
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(qjksrq!=null&&!qjksrq.equals("")) {
            date =  simpleDateFormat.parse(qjksrq);
            qjglFrmData.setQjksrq(date);
            date = null;
      	}
        if(qjjsrq!=null&&!qjjsrq.equals(""))
              date =  simpleDateFormat.parse(qjjsrq);
          qjglFrmData.setQjjsrq(date);  
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    qjglFrmData.setQjr(qjr);
	    qjglFrmData.setQjlx(qjlx);
	    qjglFrmData.setQjrbmmc(qjrbmmc);
	    qjglFrmData.setQjshzt(qjshzt);
	    
		try {
			qjgl = qjglService.find(page, size, qjglFrmData);
			map.put("total", qjgl.getTotalElements());
			map.put("rows", qjgl.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 请假管理
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	  
		logger.info("qjglController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String   qj_idList =  req.getParameter("idList"); 
		String qjr =  req.getParameter("name");
		String name = uf.getName();
		if(  qj_idList==null||qjr==null) {
			logger.info("请输入  qj_id号和请假人");
			map.put("msg", "请输入  qj_id号和请假人");
			return map;
		}
		if(!name.equals(qjr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =   qj_idList.substring(1,   qj_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = qjglService.delete(wtidIntArr,qjr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新请假管理
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Qjgl qjgl ,HttpServletRequest req  ) {
		
		logger.info("qjglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String qjr = qjgl.getQjr();
	    if(!name.equals(qjr)) {
			logger.info("请假人应选择当前登录人");
			map.put("qjglmsg", "请假人应选择当前登录人");
			return map;
		}
	    Integer   qj_id = qjgl.getQj_id();
		if(  qj_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			qjgl.setQj_id(qj_id);
	    map = qjglService.save(qjgl);
		return map; 
	}
	 
	/*
	 * 获取请假类型
	 * data 2019/04/04
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping("/getqjlx")
	public String getqjlx() {
		logger.info("QjglController ->getqjlx begin");
	    List<Qjlx> qjList = new ArrayList<Qjlx>();
	    qjList = qjlxrepository.getQjlx();
//	    for (qjlx qjlx : qjlist) {
//			system.out.println(qjlx.get);
//		}
	    return JSON.toJSONString(qjList);
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
	    String   qj_id_list =  req.getParameter("qr_id_list");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =   qj_id_list.substring(1,   qj_id_list.length()-1).split(",");
		
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer rows = 0;
		String qjshr = null;//做运维的时候再处理
		if(wtidIntArr!=null)
		     rows = qjglService.qrstatus(wtidIntArr,qjshr);
		map.put("rows", rows);
		return map;
	}
	 
	/*
	 * 日期转换
	 * data 2019/04/04
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
