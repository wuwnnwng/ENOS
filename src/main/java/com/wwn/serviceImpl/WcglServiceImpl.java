package com.wwn.serviceImpl;

import java.text.SimpleDateFormat;
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

import com.wwn.jpa.dao.WcglRepository;
import com.wwn.jpa.entity.Wcgl;
import com.wwn.service.WcglService;

 
@Service
public class WcglServiceImpl implements WcglService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private WcglRepository wcglrepository;
	 
	
	@Override
	public Page<Wcgl> find(Integer page, Integer size, Wcgl wcgl ) {
   
		logger.info("wcglServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "wc_id");
	      Page<Wcgl> wc  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String wcksrq = null;
	    	  String wcjsrq = null;
	    	  if(wcgl!=null) {
	    		  if(wcgl.getWcksrq()!=null) {
	    			  wcksrq = simpleDateFormat.format(wcgl.getWcksrq());
	    		  }else {
	    			  wcksrq = null;
	    		  }
	    		  if(wcgl.getWcjsrq()!=null) {
	    			  wcjsrq = simpleDateFormat.format(wcgl.getWcjsrq());
	    		  }else {
	    			  wcjsrq = null;
	    		  }
	    	  }
	      String wcshzt = wcgl.getWcshzt();
	      if(wcshzt!=null&&wcshzt.equals("null")) {
	    	  wcshzt ="";
	      }
	      wc = wcglrepository.find( wcgl.getWcr() ,wcgl.getWcrbmmc(),wcgl.getWcglxm() ,wcshzt, wcksrq,wcjsrq, pageable);
	      return wc;
		} catch (Exception e) {
			e.printStackTrace();
		   return wc;
		}  
	}

	@Override
	public Integer delete(List<Integer> wc_id_list,String wcr) {
		
		logger.info("wcglServiceImpl -> delete");
		Integer result = 0;
		if(wc_id_list.size()>0) {
			  try {
				result = wcglrepository.delete(wc_id_list,wcr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Wcgl wcgl) {
		
		logger.info("wcglServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(wcgl==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		if(wcgl.getWcshzt()==null||wcgl.getWcshzt().equals("")) {
			wcgl.setWcshzt("未审核");
		}
		Wcgl wcglSave = null;
		try {
			wcglSave = wcglrepository.save(wcgl);
			map.put("wcglmsg", "信息保存成功!");
			map.put("wcglSave", wcglSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("wcglmsg", "信息保存失败!");
			map.put("wcglSave", wcglSave);
			return map;
		}
		
	}
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/04/04
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> wc_id_list) {
		logger.info("wcglServiceImpl ->qrstatus begin");
		Integer row = 0;
		try {
			row = wcglrepository.qrstatus(wc_id_list);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			return row;
		}
		
	}

}
