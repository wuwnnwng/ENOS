package com.wwn.serviceImpl;

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

import com.wwn.jpa.dao.GzjlRepository;
import com.wwn.jpa.entity.Gzjl;
import com.wwn.service.GzjlService;
@Service
public class GzjlServiceImpl implements GzjlService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private GzjlRepository gzjlrepository;
	
	/*
     *  分页加模糊匹配
     * return  List<Gzjl> list 
     * date 2019/03/26
     * */
	@Override
	public Page<Gzjl> find(Integer page, Integer size, Gzjl gzjl) {
   
		logger.info("GzjlServiceImpl ->find delete");
		 Page<Gzjl> gj  = null;
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "gj_id");
	     try {
	    	  gj = gzjlrepository.find(gzjl.getBmmc(), gzjl.getXmmc(),gzjl.getJlrxm(),gzjl.getGznr(),pageable);
	    	 return gj;
		} catch (Exception e) {
			e.printStackTrace();
		   return gj;
		}  
	}

	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/30
	 * */
	@Override
	public Integer delete(List<Integer> gj_id_list,String jlrxm) {
		
		logger.info("GzjlServiceImpl ->save delete");
		Integer result = 0;
		if(gj_id_list.size()>0) {
			try {
				result = gzjlrepository.delete(gj_id_list,jlrxm);
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> save(Gzjl gzjl) {
		
		logger.info("GzjlServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(gzjl==null ) {
			map.put("gzjlmsg", "工作记录不能为空!");
			return map;
		}
		try {
			Gzjl gzjlSave = gzjlrepository.save(gzjl);
			map.put("gzjlmsg", "工作记录保存成功!");
			map.put("gzjlSave", gzjlSave);
			return map;
		} catch (Exception e) {
			map.put("gzjlmsg", "工作记录保存出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
	}
	 

}
