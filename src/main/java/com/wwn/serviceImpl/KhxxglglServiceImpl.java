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

import com.wwn.jpa.dao.KhxxglRepository;
import com.wwn.jpa.entity.Khxxgl;
import com.wwn.service.KhxxglService;


@Service
public class KhxxglglServiceImpl implements KhxxglService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private KhxxglRepository khxxglrepository;
	 
	
	@Override
	public Page<Khxxgl> find(Integer page, Integer size, Khxxgl khxxgl ) {
   
		logger.info("khxxglServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "kh_id");
	      Page<Khxxgl> kh  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String khhzkssq = null;
	    	  String khhzjsrq = null;
	    	  if(khxxgl!=null) {
	    		  if(khxxgl.getKhhzkssq()!=null) {
	    			  khhzkssq = simpleDateFormat.format(khxxgl.getKhhzkssq());
	    		  }else {
	    			  khhzkssq = null;
	    		  }
	    		  if(khxxgl.getKhhzjsrq()!=null) {
	    			  khhzjsrq = simpleDateFormat.format(khxxgl.getKhhzjsrq());
	    		  }else {
	    			  khhzjsrq = null;
	    		  }
	    	  }
	      String khhzzt = khxxgl.getKhhzzt();
	      if(khhzzt!=null&&khhzzt.equals("null")) {
	    	  khhzzt ="";
	      }
	      kh = khxxglrepository.find( khxxgl.getKhname(),khhzzt ,khhzkssq,khhzjsrq, pageable);
	      return kh;
		} catch (Exception e) {
			e.printStackTrace();
		   return kh;
		}  
	}

	@Override
	public Integer delete(List<Integer> kh_id_list,String khcjr) {
		
		logger.info("khxxglServiceImpl -> delete");
		Integer result = 0;
		if(kh_id_list.size()>0) {
			  try {
				result = khxxglrepository.delete(kh_id_list,khcjr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Khxxgl khxxgl) {
		
		logger.info("khxxglServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(khxxgl==null ) {
			map.put("mkh", "信息不能为空!");
			return map;
		}
		if(khxxgl.getKhhzzt()==null||khxxgl.getKhhzzt().equals("")) {
			khxxgl.setKhhzzt("进行中");
		}
		Khxxgl khxxglSave = null;
		try {
			khxxglSave = khxxglrepository.save(khxxgl);
			map.put("khxxglmsg", "信息保存成功!");
			map.put("khxxglSave", khxxglSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("khxxglmsg", "信息保存失败!");
			map.put("khxxglSave", khxxglSave);
			return map;
		}
		
	}
	 
}
