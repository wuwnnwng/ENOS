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

import com.wwn.jpa.entity.Hyjl;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.service.HyjlService;
import com.wwn.util.PJCommon;
@RestController
@RequestMapping("/hyjl")
public class HyjlController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private HyjlService hyjlService;
	 
	/*
	 * 分页加模糊查询
	 * data 2019/03/31
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public Map<String ,Object> find(@RequestParam(name="page",required=false)Integer page ,
			                        @RequestParam(name="size",required=false)Integer size ,HttpServletRequest  request) {
	    
		logger.info("HyjlController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Hyjl HyjlFrmData = new Hyjl();
		Page<Hyjl> hyjl;
		
	    Map gjcxMap = PJCommon.getRequestParamMap(request);
	    String hybmmc = (String) gjcxMap.get("hybmmccx");//部门名称
	    String hyxmmc = (String) gjcxMap.get("hyxmmccx");//项目名称
	    String hycyry = (String) gjcxMap.get("hycyrycx");//会议参与人员
	    String hyyt = (String) gjcxMap.get("hyytcx");//会议议题
	    String hyrq =  (String) gjcxMap.get("hyrqcx");//议题日期
	    String hyjlrxm = (String) gjcxMap.get("hyjlrxmcx");//会议记录人
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        //必须捕获异常
        try {
        	Date date = null;
        	if(hyrq!=null&&!hyrq.equals(""))
                date =  simpleDateFormat.parse(hyrq);
            HyjlFrmData.setHyrq(date);
            System.out.println(date);
        } catch(ParseException px) {
            px.printStackTrace();
            map.put("total", 0);
			map.put("rows", null);
        }
	    
	    HyjlFrmData.setHybmmc(hybmmc);
	    HyjlFrmData.setHyxmmc(hyxmmc);
	    HyjlFrmData.setHycyry(hycyry);
	    HyjlFrmData.setHyyt(hyyt);
//	    HyjlFrmData.setHyrq(hyrq);
	    HyjlFrmData.setHyjlrxm(hyjlrxm);
	    
		try {
			hyjl = hyjlService.find(page, size, HyjlFrmData);
			if(hyjl!=null) {
				map.put("total", hyjl.getTotalElements());
				map.put("rows", hyjl.getContent());
			}else {
				map.put("total", 0);
				map.put("rows", null);
			}
			
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}
	 
	/*
	 * delete 会议记录
	 * data 2019/03/31
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> deleteXxfb(HttpServletRequest req) {
	    
		logger.info("HyjlController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String hj_idList =  req.getParameter("idList"); 
		String hyjlrxm =  req.getParameter("name");
		String name = uf.getName();
		if(hj_idList==null||hyjlrxm==null) {
			logger.info("请输入hj_id号和操作人");
			map.put("msg", "请输入hj_id号和操作人");
			return map;
		}
		if(!name.equals(hyjlrxm)) {
			logger.info("只能删除你创建的会议记录");
			map.put("msg", "只能删除你创建的会议记录");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = hj_idList.substring(1, hj_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = hyjlService.delete(wtidIntArr,hyjlrxm);
			map.put("msg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("msg", "删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新会议记录
	 * data 2019/03/31
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Hyjl hyjl ,HttpServletRequest req  ) {
		
		logger.info("HyjlController ->save begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String hyjlrxm = hyjl.getHyjlrxm();
//	    String hylyrxm = hyjl.getHylyrxm();
	    if(!name.equals(hyjlrxm)) {
			logger.info("操作人应选择当前登录人");
			map.put("hyjlmsg", "操作人应选择当前登录人");
			return map;
		}
	    Integer hj_id = hyjl.getHj_id();
		if(hj_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			hyjl.setHj_id(hj_id);
	    map = hyjlService.save(hyjl);
		return map; 
	}
	 
	/*
	 * 留言会议记录
	 * data 2019/03/31
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/liuyan", method = RequestMethod.POST)
	public Map<String,Object> liuyan (Hyjl hyjl ,HttpServletRequest req  ) {
		
		logger.info("HyjlController ->save begin");
		Map<String, Object> map = new HashMap<>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
//	    String name = uf.getName();
	    String permission = uf.getRoleList().get(0).getPermissions().get(0).getPermission();
//	    String hyjlrxm = hyjl.getHyjlrxm();
//	    String hylyrxm = hyjl.getHylyrxm();
	    if(!permission.equals("all")) {
			logger.info("操作人没有权限");
			map.put("hyjlmsg", "操作人没有权限");
			return map;
		}
	    Integer hj_id = hyjl.getHj_id();
		if(hj_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			hyjl.setHj_id(hj_id);
	    map = hyjlService.save(hyjl);
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
