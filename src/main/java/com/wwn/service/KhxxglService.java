package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Khxxgl;

public interface KhxxglService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/06
	 * @return Page<khxxgl>
	 */
  
	public Page<Khxxgl> find(Integer page, Integer size,Khxxgl khxxgl );
  
	/*
   * 根据用户的kh_id  删除客户管理
   * data 2019/04/06
   * @return Integer
   * */
   public Integer delete(List<Integer> kh_id_list,String khcjr);
  
   /*
    * 保存或者修改客户管理
    * data 2019/04/06
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Khxxgl khxxgl);
  
    
	
	
	
}
