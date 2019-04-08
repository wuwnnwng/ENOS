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

import com.wwn.jpa.dao.RyqkRepository;
import com.wwn.jpa.entity.Ryqk;
import com.wwn.service.RyqkService;

@Service
public class RyqkServiceImpl implements RyqkService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private RyqkRepository ryqkrepository;
	 
	
	@Override
	public Page<Ryqk> find(Integer page, Integer size, Ryqk ryqk ) {
   
		logger.info("ryqkServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "rq_id");
	      Page<Ryqk> rq  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String rqksrq = null;
	    	  if(ryqk!=null) {
	    		  if(ryqk.getRqksrq()!=null) {
	    			  rqksrq = simpleDateFormat.format(ryqk.getRqksrq());
	    		  }else {
	    			  rqksrq = null;
	    		  }
	    		  
	    	  }
	     
          String rqjslb = ryqk.getRqjslb();
	 	  if(rqjslb!=null&&rqjslb.equals("null")) {
	 		 rqjslb ="";
	 	  }
	      rq = ryqkrepository.find( ryqk.getRqcxxm() ,ryqk.getRqbmmc(), ryqk.getRqxm(),rqjslb ,rqksrq, pageable);
	      return rq;
		} catch (Exception e) {
			e.printStackTrace();
		   return rq;
		}  
	}

	@Override
	public Integer delete(List<Integer> rq_id_list,String rqzprxm) {
		
		logger.info("ryqkServiceImpl -> delete");
		Integer result = 0;
		if(rq_id_list.size()>0) {
			  try {
				result = ryqkrepository.delete(rq_id_list,rqzprxm);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Ryqk ryqk) {
		
		logger.info("ryqkServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(ryqk==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
//		if(ryqk.getrqshzt()==null||ryqk.getrqshzt().equals("")) {
//			ryqk.setrqshzt("未审核");
//		}
		Ryqk ryqkSave = null;
		try {
			ryqkSave = ryqkrepository.save(ryqk);
			map.put("ryqkmsg", "信息保存成功!");
			map.put("ryqkSave", ryqkSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("ryqkmsg", "信息保存失败!");
			map.put("ryqkSave", ryqkSave);
			return map;
		}
		
	}
	 

}
