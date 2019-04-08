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

import com.wwn.jpa.dao.SsglRepository;
import com.wwn.jpa.entity.Ssgl;
import com.wwn.service.SsglService;

@Service
public class SsglServiceImpl implements SsglService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private SsglRepository ssglrepository;
	 
	
	@Override
	public Page<Ssgl> find(Integer page, Integer size, Ssgl ssgl ) {
   
		logger.info("ssglServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "sg_id");
	      Page<Ssgl> sg  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String sgksrq = null;
	    	  String sgjsrq = null;
	    	  if(ssgl!=null) {
	    		  if(ssgl.getSgksrq()!=null) {
	    			  sgksrq = simpleDateFormat.format(ssgl.getSgksrq());
	    		  }else {
	    			  sgksrq = null;
	    		  }
	    		  if(ssgl.getSgjsrq()!=null) {
	    			  sgjsrq = simpleDateFormat.format(ssgl.getSgjsrq());
	    		  }else {
	    			  sgjsrq = null;
	    		  }
	    	  }
	      String sgjbzt = ssgl.getSgjbzt();
	      if(sgjbzt!=null&&sgjbzt.equals("null")) {
	    	  sgjbzt ="";
	      }
	      sg = ssglrepository.find( ssgl.getSgsqr(),ssgl.getSgjbr(),ssgl.getSgssdz() ,ssgl.getSgssmc(), sgksrq,sgjsrq, sgjbzt,pageable);
	      return sg;
		} catch (Exception e) {
			e.printStackTrace();
		   return sg;
		}  
	}

	@Override
	public Integer delete(List<Integer> sg_id_list,String sgsqr) {
		
		logger.info("ssglServiceImpl -> delete");
		Integer result = 0;
		if(sg_id_list.size()>0) {
			  try {
				result = ssglrepository.delete(sg_id_list,sgsqr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Ssgl ssgl) {
		
		logger.info("ssglServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(ssgl==null ) {
			map.put("msg", "信息不能为空!");
			return map;
		}
		if(ssgl.getSgjbzt()==null||ssgl.getSgjbzt().equals("")) {
			ssgl.setSgjbzt("未批准");
		}
		Ssgl ssglSave = null;
		try {
			ssglSave = ssglrepository.save(ssgl);
			map.put("ssglmsg", "信息保存成功!");
			map.put("ssglSave", ssglSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("ssglmsg", "信息保存失败!");
			map.put("ssglSave", ssglSave);
			return map;
		}
		
	}
	 /*
	 * 修改审核转态
	 * return String
	 * date 2019/04/04
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> sg_id_list,String sgshr) {
		logger.info("ssglServiceImpl ->qrstatus begin");
		Integer row = 0;
		try {
			row = ssglrepository.qrstatus(sg_id_list, sgshr);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			return row;
		}
		
	}
 
}
