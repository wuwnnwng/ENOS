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

import com.wwn.jpa.dao.HyjlRepository;
import com.wwn.jpa.entity.Gzjl;
import com.wwn.jpa.entity.Hyjl;
import com.wwn.service.HyjlService;
@Service
public class HyjlServiceImpl implements HyjlService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private HyjlRepository hyjlrepository;
	
	/*
     *  分页加模糊匹配
     * return  List<Gzjl> list 
     * date 2019/03/26
     * */
	@Override
	public Page<Hyjl> find(Integer page, Integer size, Hyjl hyjl) {
   
		logger.info("HyjlServiceImpl ->find begin");
		 Page<Hyjl> hj  = null;
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "hj_id");
	     try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String hyrq = null;
	    	  if(hyjl!=null) {
	    		  if(hyjl.getHyrq()!=null) {
	    			  hyrq = simpleDateFormat.format(hyjl.getHyrq());
	    		  }else {
	    			  hyrq = null;
	    		  }
	    		  
	    	  }
//	      	  String name =   hyjl.getHybmmc(); 
	    	 hj = hyjlrepository.find( hyjl.getHybmmc(),hyjl.getHyxmmc(),hyjl.getHyjlrxm(),hyjl.getHycyry(),hyjl.getHyyt(),hyrq,pageable);
	    	 return hj;
		} catch (Exception e) {
			e.printStackTrace();
		   return hj;
		}  
	}

	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/31
	 * */
	@Override
	public Integer delete(List<Integer> hj_id_list,String hyjlrxm) {
		
		logger.info("HyjlServiceImpl ->delete begin");
		Integer result = 0;
		if(hj_id_list.size()>0) {
			try {
				result = hyjlrepository.delete(hj_id_list,hyjlrxm);
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Hyjl hyjl) {
		
		logger.info("HyjlServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(hyjl==null ) {
			map.put("gzjlmsg", "工作记录不能为空!");
			return map;
		}
//		System.out.println(hyjl.getHyjl() instanceof String);
		try {
			Hyjl hyjlSave = hyjlrepository.save(hyjl);
			map.put("hyjlmsg", "会议记录保存成功!");
			map.put("hyjlSave", hyjlSave);
			return map;
		} catch (Exception e) {
			map.put("hyjlmsg", "会议记录保存出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
	}
	 

}
