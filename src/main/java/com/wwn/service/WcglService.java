package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Wcgl;
public interface WcglService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/04
	 * @return Page<wcgl>
	 */
  
	public Page<Wcgl> find(Integer page, Integer size,Wcgl wcgl );
  
	/*
   * 根据用户的wc_id  删除外出管理
   * data 2019/04/04
   * @return Integer
   * */
   public Integer delete(List<Integer> wc_id_list,String wcr);
  
   /*
    * 保存或者修改外出管理
    * data 2019/04/04
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Wcgl wcgl);
  
    /*
	 * 修改确认转态
	 * return String
	 * data 2019/04/04
	 * */
	public Integer qrstatus(List<Integer> wc_id_list );
	
	
	
	
}
