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

import com.wwn.jpa.entity.Ssgl;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.SsglService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/ssgl")
public class SsglController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private SsglService ssglService; 
	 
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
	    
		logger.info("ssglController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Ssgl ssglFrmData = new Ssgl();
		Page<Ssgl> ssgl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String sgsqr = (String) gjcxMap.get("sgsqr");//宿舍申请人
	    String sgjbr = (String) gjcxMap.get("sgjbr");//宿舍管理 经办人
	    String sgksrq = (String) gjcxMap.get("sgksrq");//宿舍管理开始日期
	    String sgjsrq = (String) gjcxMap.get("sgjsrq");//宿舍管理结束日期
	    String sgssmc = (String) gjcxMap.get("sgssmc");//宿舍名称
	    String sgssdz = (String) gjcxMap.get("sgssdz");//宿舍地址
	    String sgjbzt = (String) gjcxMap.get("sgjbzt");//经办人审核转态：已批准，未批准
	    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
      //必须捕获异常
      try {
      	Date date = null;
      	if(sgksrq!=null&&!sgksrq.equals("")) {
            date =  simpleDateFormat.parse(sgksrq);
            ssglFrmData.setSgksrq(date);
            date = null;
      	}
        if(sgjsrq!=null&&!sgjsrq.equals(""))
              date =  simpleDateFormat.parse(sgjsrq);
          ssglFrmData.setSgjsrq(date);  
      } catch(ParseException px) {
          px.printStackTrace();
          map.put("total", 0);
		  map.put("rows", null);
      }
	    ssglFrmData.setSgsqr(sgsqr);
	    ssglFrmData.setSgjbr(sgjbr);
	    ssglFrmData.setSgssdz(sgssdz);
	    ssglFrmData.setSgssmc(sgssmc);
	    ssglFrmData.setSgjbzt(sgjbzt);
	    
		try {
			ssgl = ssglService.find(page, size, ssglFrmData);
			map.put("total", ssgl.getTotalElements());
			map.put("rows", ssgl.getContent());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 宿舍管理
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> delete(HttpServletRequest req) {
	  
		logger.info("ssglController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String   sg_idList =  req.getParameter("idList"); 
		String sgsqr =  req.getParameter("name");
		String name = uf.getName();
		if(  sg_idList==null||sgsqr==null) {
			logger.info("请输入  sg_id号和请假人");
			map.put("msg", "请输入  sg_id号和请假人");
			return map;
		}
		if(!name.equals(sgsqr)) {
			logger.info("只能删除你自己的记录");
			map.put("msg", "只能删除你自己的记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =   sg_idList.substring(1,   sg_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = ssglService.delete(wtidIntArr,sgsqr);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新宿舍管理
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Ssgl ssgl ,HttpServletRequest req  ) {
		
		logger.info("ssglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String sgsqr = ssgl.getSgsqr();
	    if(!name.equals(sgsqr)) {
			logger.info("请假人应选择当前登录人");
			map.put("ssglmsg", "请假人应选择当前登录人");
			return map;
		}
	    Integer   sg_id = ssgl.getSg_id();
		if(sg_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			ssgl.setSg_id(sg_id);
	    map = ssglService.save(ssgl);
		return map; 
	}
	/*
	 * 审核宿舍管理
	 * data 2019/04/06
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/shxssgl", method = RequestMethod.POST)
	public Map<String,Object> shxssgl(Ssgl ssgl ,HttpServletRequest req  ) {
		
		logger.info("ssglController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String permission = uf.getRoleList().get(0).getPermissions().get(0).getPermission();
	    if(!permission.equals("all")) {
	    	logger.info("操作人没有权限");
			map.put("xmdsjmsg", "操作人没有权限");
			return map;
		}
	    String xdshzt = ssgl.getSgjbzt();
	    if(xdshzt.equals("未批准")) {
	    	ssgl.setSgjbzt("已批准");
	    }
	    Integer   sg_id = ssgl.getSg_id();
		if(sg_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			ssgl.setSg_id(sg_id);
	    map = ssglService.save(ssgl);
		return map; 
	}
	 
	 
	
	/*
	 * 修改审核转态
	 * return String
	 * date 2019/04/06 
	 * */
	@RequestMapping(value = "/qrstatus", method = RequestMethod.POST)
	public Map<String ,Object> qrstatus( HttpServletRequest req ) {
		
		logger.info("gzlyController ->savePerson begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    String   sg_id_list =  req.getParameter("qr_id_list");
	    List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr =   sg_id_list.substring(1,   sg_id_list.length()-1).split(",");
		
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer rows = 0;
		String qjshr = null;//做运维的时候再处理
		if(wtidIntArr!=null)
		     rows = ssglService.qrstatus(wtidIntArr,qjshr);
		map.put("rows", rows);
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
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
     
}
