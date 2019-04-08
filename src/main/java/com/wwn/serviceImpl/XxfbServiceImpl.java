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
import com.wwn.jpa.dao.XxfbRepository;
import com.wwn.jpa.entity.Xxfb;
import com.wwn.service.XxfbService;
@Service
public class XxfbServiceImpl implements XxfbService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired 
	private XxfbRepository xxfbRepository;
	
	@Override
	public Page<Xxfb> find(Integer page, Integer size, String field) {
   
		logger.info("XxfbServiceImpl ->find delete");
		if (null == page) {
		      page = 0;
	      }
	      if (size==null) {
	          size = 10;
	      }
	      PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "xf_id");
	      Page<Xxfb> xxfb =   xxfbRepository.find(field, pageable);
	      return xxfb;
	}

	@Override
	public Integer deleteXxfb(List<Integer> xf_id_list,String fbr) {
		
		logger.info("XxfbServiceImpl ->save delete");
		Integer result = 0;
		if(xf_id_list.size()>0)
		  result = xxfbRepository.deleteXxfb(xf_id_list,fbr);
		return result;
	}

	@Override
	public Map<String, Object> save(Xxfb xxfb) {
		
		logger.info("XxfbServiceImpl ->save begin");
		Map<String,Object> map = new  HashMap<String, Object>();
		if(xxfb==null ) {
			map.put("xxfbmsg", "发布信息不能为空!");
			return map;
		}
		try {
			Xxfb xxfbSave = xxfbRepository.save(xxfb);
			map.put("xxfbmsg", "发布信息保存成功!");
			map.put("person", xxfbSave);
			return map;
		} catch (Exception e) {
			map.put("xxfbmsg", "发布信息保存出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
	}
	 

}
