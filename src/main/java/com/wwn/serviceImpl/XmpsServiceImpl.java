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

import com.wwn.jpa.dao.XmpsRepository;
import com.wwn.jpa.entity.Xmps;
import com.wwn.service.XmpsService;

@Service
public class XmpsServiceImpl implements XmpsService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private XmpsRepository xmpsrepository;
	 
	
	@Override
	public Page<Xmps> find(Integer page, Integer size, Xmps xmps ) {
   
		logger.info("xmpsServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "xp_id");
	      Page<Xmps> xp  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String xprq = null;
	    	  if(xmps!=null) {
	    		  if(xmps.getXprq()!=null) {
	    			  xprq = simpleDateFormat.format(xmps.getXprq());
	    		  }else {
	    			  xprq = null;
	    		  }
	    		  
	    	  }
	      xp = xmpsrepository.find( xmps.getXpxmmc() ,xmps.getXpr() ,xprq, pageable);
	      return xp;
		} catch (Exception e) {
			e.printStackTrace();
		   return xp;
		}  
	}

	@Override
	public Integer delete(List<Integer> xp_id_list,String xpr) {
		
		logger.info("xmpsServiceImpl -> delete");
		Integer result = 0;
		if(xp_id_list.size()>0) {
			  try {
				result = xmpsrepository.delete(xp_id_list,xpr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Xmps xmps) {
		
		logger.info("xmpsServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(xmps==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
//		if(xmps.getxpshzt()==null||xmps.getxpshzt().equals("")) {
//			xmps.setxpshzt("未审核");
//		}
		Xmps xmpsSave = null;
		try {
			xmpsSave = xmpsrepository.save(xmps);
			map.put("xmpsmsg", "信息保存成功!");
			map.put("xmpsSave", xmpsSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("xmpsmsg", "信息保存失败!");
			map.put("xmpsSave", xmpsSave);
			return map;
		}
		
	}
	 

}
