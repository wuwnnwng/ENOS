package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Xmdsj;
public interface XmdsjService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/01
	 * @return Page<Xmdsj>
	 */
  
	public Page<Xmdsj> find(Integer page, Integer size,Xmdsj xmdsj );
  
	/*
   * 根据用户的gl_id 删除用户信息
   * data 2019/04/01
   * @return Integer
   * */
   public Integer delete(List<Integer> xd_id_list,String xdjlr);
  
   /*
    * 保存或者修改项目大事记
    * data 2019/04/02
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Xmdsj xmdsj);
  
    /*
	 * 修改确认转态
	 * return String
	 * data 2019/04/02
	 * */
	public Integer qrstatus(List<Integer> xd_id_list );
	
	
	
	
}
