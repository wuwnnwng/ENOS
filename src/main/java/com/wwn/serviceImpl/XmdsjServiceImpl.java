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

import com.wwn.jpa.dao.XmdsjRepository;
import com.wwn.jpa.entity.Xmdsj;
import com.wwn.service.XmdsjService;
@Service
public class XmdsjServiceImpl implements XmdsjService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private XmdsjRepository xmdsjrepository;
	 
	
	@Override
	public Page<Xmdsj> find(Integer page, Integer size, Xmdsj xmdsj ) {
   
		logger.info("xmdsjServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "xd_id");
	      Page<Xmdsj> xd  = null;
	      try {
	    	  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	  String xdrq = null;
	    	  if(xmdsj!=null) {
	    		  if(xmdsj.getXdrq()!=null) {
	    			  xdrq = simpleDateFormat.format(xmdsj.getXdrq());
	    		  }else {
	    			  xdrq = null;
	    		  }
	    		  
	    	  }
	      String xdshzt = xmdsj.getXdshzt();
	      if(xdshzt!=null&&xdshzt.equals("null")) {
	    	  xdshzt ="";
	      }
	      String shr = xmdsj.getXdshr();
//	      xd = xmdsjrepository.find( xmdsj.getXdxmmc(),xmdsj.getXdjlr(),xmdsj.getXdshr(),xdshzt,xdrq,pageable);
	      xd = xmdsjrepository.find( xmdsj.getXdxmmc() ,xmdsj.getXdjlr(), xdshzt, xdrq,xmdsj.getXdshr(), pageable);
	      return xd;
		} catch (Exception e) {
			e.printStackTrace();
		   return xd;
		}  
	}

	@Override
	public Integer delete(List<Integer> xd_id_list,String xdjlr) {
		
		logger.info("xmdsjServiceImpl -> delete");
		Integer result = 0;
		if(xd_id_list.size()>0) {
			  try {
				result = xmdsjrepository.delete(xd_id_list,xdjlr);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Xmdsj xmdsj) {
		
		logger.info("xmdsjServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(xmdsj==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		if(xmdsj.getXdshzt()==null||xmdsj.getXdshzt().equals("")) {
			xmdsj.setXdshzt("未审核");
		}
		Xmdsj xmdsjSave = null;
		try {
			xmdsjSave = xmdsjrepository.save(xmdsj);
			map.put("xmdsjmsg", "信息保存成功!");
			map.put("xmdsjSave", xmdsjSave);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("xmdsjmsg", "信息保存失败!");
			map.put("xmdsjSave", xmdsjSave);
			return map;
		}
		
	}
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/04/01
	 * */ 
	@Override
	public Integer qrstatus(List<Integer> xd_id_list) {
		logger.info("xmdsjServiceImpl ->qrstatus begin");
		Integer row = 0;
		try {
			row = xmdsjrepository.qrstatus(xd_id_list);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
			return row;
		}
		
	}

}
