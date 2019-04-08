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

import com.wwn.jpa.dao.QjglRepository;
import com.wwn.jpa.entity.Qjgl;
import com.wwn.service.QjglService;


@Service
public class QjglServiceImpl implements QjglService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private QjglRepository qjglrepository;
	 
	
	@Override
	public Page<Qjgl> find(Integer page, Integer size, Qjgl qjgl ) {
   
		logger.info("qjglServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "qj_id");
	      Page<Qjgl> qj  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String qjksrq = null;
	    	  String qjjsrq = null;
	    	  if(qjgl!=null) {
	    		  if(qjgl.getQjksrq()!=null) {
	    			  qjksrq = simpleDateFormat.format(qjgl.getQjksrq());
	    		  }else {
	    			  qjksrq = null;
	    		  }
	    		  if(qjgl.getQjjsrq()!=null) {
	    			  qjjsrq = simpleDateFormat.format(qjgl.getQjjsrq());
	    		  }else {
	    			  qjjsrq = null;
	    		  }
	    	  }
	      String qjshzt = qjgl.getQjshzt();
	      if(qjshzt!=null&&qjshzt.equals("null")) {
	    	  qjshzt ="";
	      }
	      qj = qjglrepository.find( qjgl.getQjr() ,qjgl.getQjrbmmc(),qjgl.getQjlx() ,qjshzt, qjksrq,qjjsrq, pageable);
	      return qj;
		} catch (Exception e) {
			e.printStackTrace();
		   return qj;
		}  
	}

	@Override
	public Integer delete(List<Integer> qj_id_list,String qjr) {
		
		logger.info("qjglServiceImpl -> delete");
		Integer result = 0;
		if(qj_id_list.size()>0) {
			  try {
				result = qjglrepository.delete(qj_id_list,qjr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Qjgl qjgl) {
		
		logger.info("qjglServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(qjgl==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		if(qjgl.getQjshzt()==null||qjgl.getQjshzt().equals("")) {
			qjgl.setQjshzt("未审核");
		}
		Qjgl qjglSave = null;
		try {
			qjglSave = qjglrepository.save(qjgl);
			map.put("qjglmsg", "信息保存成功!");
			map.put("qjglSave", qjglSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("qjglmsg", "信息保存失败!");
			map.put("qjglSave", qjglSave);
			return map;
		}
		
	}
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/04/04
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> qj_id_list,String qjshr) {
		logger.info("qjglServiceImpl ->qrstatus begin");
		Integer row = 0;
		try {
			row = qjglrepository.qrstatus(qj_id_list, qjshr);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			return row;
		}
		
	}
 
}
