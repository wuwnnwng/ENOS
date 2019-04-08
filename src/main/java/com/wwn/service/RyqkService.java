package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Ryqk;
public interface RyqkService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/03
	 * @return Page<Ryqk>
	 */
  
	public Page<Ryqk> find(Integer page, Integer size,Ryqk ryqk );
  
	/*
   * 根据用户的rq_id 删除用户信息
   * data 2019/04/03
   * @return Integer
   * */
   public Integer delete(List<Integer> rq_id_list,String rqzprxm);
  
   /*
    * 保存或者修改人员情况
    * data 2019/04/03
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Ryqk ryqk);
  
    
	
	
	
	
}
