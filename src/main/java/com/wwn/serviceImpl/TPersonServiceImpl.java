package com.wwn.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wwn.jpa.dao.TPersonRepository;
import com.wwn.jpa.entity.TPerson;
import com.wwn.service.TPersonService;
@Service
public class TPersonServiceImpl implements TPersonService {
   
	@Autowired
	private TPersonRepository tPersonRepository;

	@Override
	public Page<TPerson> find(Integer page, Integer size) {
	    if (null == page) {
	            page = 0;
        }
        if (size==null) {
            size = 10;
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "pid");
        Page<TPerson> tPerson = tPersonRepository.findAll(pageable);
//        Page<TPerson> tPerson =   tPersonRepository.searchPersonPag("3", pageable);
        return tPerson;
	}

	@Override
	public Integer deletePerson(List<Integer> pidList) {
		Integer result = 0;
		if(pidList.size()>0)
		  result = tPersonRepository.deletePerson(pidList);
		return result;
	}

	@Override
	public Map<String,Object> save(TPerson tPerson) {
		Map<String,Object> map = new  HashMap<String, Object>();
		if(tPerson==null ) {
			map.put("msg", "用户信息不能为空!");
			return map;
		}
		TPerson person = tPersonRepository.save(tPerson);
		map.put("msg", "用户信息保存成功!");
		map.put("person", person);
		return map;
			
	}

	@Override
	public List<TPerson> searchPerson(String field,Integer page ,Integer size) {
		List<TPerson> list = new ArrayList<TPerson>();
		list = tPersonRepository.searchPerson(field,page ,size);
		return list;
	}

	@Override
	public Integer getPersonCount(String field,Integer page ,Integer size) {
		Integer count = tPersonRepository.getPersonCount(field,page,size);
		return count;
	}

}
