package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Xmps;

public interface XmpsService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/03
	 * @return Page<xmps>
	 */
  
	public Page<Xmps> find(Integer page, Integer size,Xmps xmps );
  
	/*
   * 根据用户的xp_id 删除项目评审
   * data 2019/04/03
   * @return Integer
   * */
   public Integer delete(List<Integer> xp_id_list,String xpr);
  
   /*
    * 保存或者修改项目评审
    * data 2019/04/03
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Xmps xmps);
  
    
	
	
	
	
}
