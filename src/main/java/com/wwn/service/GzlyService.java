package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Gzly;
public interface GzlyService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/01
	 * @return Page<TPerson>
	 */
  
	public Page<Gzly> find(Integer page, Integer size,Gzly gzly,String dlzh);
  
	/*
   * 根据用户的gl_id 删除用户信息
   * data 2019/04/01
   * @return Integer
   * */
   public Integer delete(List<Integer> gl_id_list,String fjr);
  
   /*
    * 保存或者修改用户信息
    * data 2019/03/26
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Gzly gzly);
  
    /*
	 * 修改确认转态
	 * return String
	 * data 2019/03/26
	 * */
	public Integer qrstatus(List<Integer> gl_id_list );
	
	
	
	
}
