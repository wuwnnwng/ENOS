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

import com.wwn.jpa.dao.GzlyRepository;
import com.wwn.jpa.entity.Gzly;
import com.wwn.service.GzlyService;
@Service
public class GzlyServiceImpl implements GzlyService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private GzlyRepository gzlyrepository;
	 
	
	@Override
	public Page<Gzly> find(Integer page, Integer size, Gzly gzly,String dlzh) {
   
		logger.info("GzlyServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "gl_id");
	      Page<Gzly> gl  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String fjrq = null;
	    	  if(gzly!=null) {
	    		  if(gzly.getFjrq()!=null) {
	    			  fjrq = simpleDateFormat.format(gzly.getFjrq());
	    		  }else {
	    			  fjrq = null;
	    		  }
	    		  
	    	  }
//	      String name = gzly.getFjr();
	      String lystatus = gzly.getLystatus();
	      if(lystatus!=null&&lystatus.equals("null")) {
	    	  lystatus ="";
	      }
    	  gl = gzlyrepository.find( gzly.getFjr(),fjrq,lystatus,dlzh,pageable);
//    	  gl = gzlyrepository.find( gzly.getFjr()  ,fjrq,dlzh,pageable);
     	  return gl;
		} catch (Exception e) {
			e.printStackTrace();
		   return gl;
		}  
	}

	@Override
	public Integer delete(List<Integer> gl_id_list,String fjr) {
		
		logger.info("GzlyServiceImpl -> delete");
		Integer result = 0;
		if(gl_id_list.size()>0) {
			  try {
				result = gzlyrepository.delete(gl_id_list,fjr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Gzly gzly) {
		
		logger.info("GzlyServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(gzly==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		if(gzly.getLystatus()==null||gzly.getLystatus().equals("")) {
			gzly.setLystatus("未确认");
		}
		Gzly gzlySave = null;
		try {
			gzlySave = gzlyrepository.save(gzly);
			map.put("gzlymsg", "用户信息保存成功!");
			map.put("gzlySave", gzlySave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("gzlymsg", "用户信息保存失败!");
			map.put("gzlySave", gzlySave);
			return map;
		}
		
	}
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/04/01
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> gl_id_list) {
		logger.info("WdtxServiceImpl ->qrstatus begin");
		Integer row = 0;
		try {
			row = gzlyrepository.qrstatus(gl_id_list);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			return row;
		}
		
	}

}
