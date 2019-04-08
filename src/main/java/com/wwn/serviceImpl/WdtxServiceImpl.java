package com.wwn.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wwn.jpa.dao.UserInfoRepository;
import com.wwn.jpa.dao.WdtxRepository;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Wdtx;
import com.wwn.service.WdtxService;
@Service
public class WdtxServiceImpl implements WdtxService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private WdtxRepository wdtxRepository;
	@Autowired 
	private UserInfoRepository userInfoRepository;
	
	@Override
	public Page<Wdtx> find(Integer page, Integer size, String field,String dlzh) {
   
		logger.info("WdtxServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "wt_id");
	      Page<Wdtx> wdtx =   wdtxRepository.find(field, dlzh,pageable);
	      return wdtx;
	}

	@Override
	public Integer delete(List<Integer> wt_id_list,String fbr) {
		
		logger.info("WdtxServiceImpl ->save delete");
		Integer result = 0;
		if(wt_id_list.size()>0)
		  result = wdtxRepository.deleteWdtx(wt_id_list,fbr);
		return result;
	}

	@Override
	public Map<String, Object> save(Wdtx wdtx) {
		
		logger.info("WdtxServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(wdtx==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		if(wdtx.getStatus()==null) {
			wdtx.setStatus(0);
		}
		if(wdtx.getStadec()==null||wdtx.getStadec().equals("")) {
			wdtx.setStadec("未确认");
		}
		Wdtx person = wdtxRepository.save(wdtx);
		map.put("wdtxmsg", "用户信息保存成功!");
		map.put("person", person);
		return map;
	}
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/03/26
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> wt_id) {
		logger.info("WdtxServiceImpl ->qrstatus begin");
		Integer row = wdtxRepository.qrstatus(wt_id);
		return row;
	}

	/*
	 * 等到员工新姓名
	 *  List<UserInfo> 
	 * date 2019/03/27
	 * */
	@Override
	public List<String> getUserName() {
		logger.info("WdtxServiceImpl ->getUserName begin");
		List<String> ufList = new  ArrayList<String>();
		ufList = userInfoRepository.getUserName();
		return ufList;
	}

}
