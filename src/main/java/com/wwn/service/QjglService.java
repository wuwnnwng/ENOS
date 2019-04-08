package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Qjgl;
public interface QjglService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/04
	 * @return Page<qjgl>
	 */
  
	public Page<Qjgl> find(Integer page, Integer size,Qjgl qjgl );
  
	/*
   * 根据用户的qj_id  删除外出管理
   * data 2019/04/04
   * @return Integer
   * */
   public Integer delete(List<Integer> qj_id_list,String qjr);
  
   /*
    * 保存或者修改外出管理
    * data 2019/04/04
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Qjgl qjgl);
  
    /*
	 * 修改确认转态
	 * return String
	 * data 2019/04/04
	 * */
	Integer qrstatus(List<Integer> qj_id_list, String qjshr);
	
	
	
	
}
