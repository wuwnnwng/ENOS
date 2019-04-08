package com.wwn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wwn.jpa.dao.KqtjRepository;
import com.wwn.jpa.entity.Kqtj;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.util.PJCommon;

@RestController
@RequestMapping("/kqtj")
public class KqtjController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KqtjRepository kqtjrepository;

	/*
	 * 分页加模糊查询 data 2019/03/30 吴万能 return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public Map<String, Object> find(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size, HttpServletRequest request) {

		logger.info("kqtjController ->find begin");
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();// 获取session 中当前登录的用户的信息
		UserInfo uf = (UserInfo) session.getAttribute("userInfo");

		Page<Kqtj> kqtj;
		Map kqtjMap = PJCommon.getRequestParamMap(request);
		String year = (String) kqtjMap.get("year");// 年份
		String month = (String) kqtjMap.get("month");// 月份
		String dlzh = uf.getName();

		if (year == null || year.equals("") || month == null || month.equals("")) {
			map.put("kqtjmsg", "月份或者年份不能为空");
			return map;
		}
		if (null == page) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}
		PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "dk_id");

		try {
			kqtj = kqtjrepository.find(year, month, dlzh, pageable);
			map.put("total", kqtj.getTotalElements());
			map.put("rows", kqtj.getContent());
			map.put("kqtjmsg", "查询考情统计成功");
		} catch (Exception e) {
			map.put("kqtjmsg", "查询考情统计出错");
			map.put("total", 0);
			map.put("rows", null);
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * 日期转换 data 2019/03/30 吴万能 return Map<String, Object>
	 * 
	 */
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	public static void main(String[] args) {
       
		Dxc dxc = new Dxc();
		Thread th1 = new Thread(dxc,"线程一");
		Thread th2 = new Thread(dxc,"线程二");
		Thread th3 = new Thread(dxc,"线程三");
		Thread th4 = new Thread(dxc,"线程四");
//		th1.start();
//		th2.start();
//		th3.start();
//		th4.start() ;
		Dxc dxc1 = new Dxc();
		System.out.println(dxc==dxc1);
		 Sington sington = Sington.getSington();
		Sington sington1 = Sington.getSington();
		System.out.println(sington == sington1 );
//		Sington sington = new Sington();
	}

	
}
class Sington {
	private static   Sington sington  ;
	public  static int pik = 10;
	 
	private   Sington() {}
    public static    Sington  getSington() {
    	synchronized(Sington.class) {
    		if(sington == null) {
    			sington = new Sington();
    		} 
    	}
    	return sington;
    }
    public static int subPik() {
//    	System.out.println();
    	return Sington.pik--;
    }
}	

class Dxc implements Runnable{
	@Override
	public void run() {
		while(Sington.pik>0) {
			synchronized (this) {
				if(Sington.pik<=0) {
					return;
				}
				System.out.println(Thread.currentThread().getName()+"售出第："+Sington.pik+"  票");
//				Sington.getSington().pik--;
				Sington.subPik();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Sington.pik<=0) {
					System.out.println(Thread.currentThread().getName()+"售票結束");
				}
			}
		}
	}
}




