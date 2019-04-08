package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Ssgl;
public interface SsglService {
	
	/**
	 * 用户列表(分页加模糊)
	 * data 2019/04/06
	 * @return Page<ssgl>
	 */
  
	public Page<Ssgl> find(Integer page, Integer size,Ssgl ssgl );
  
	/*
   * 根据用户的sg_id  删除宿舍管理
   * data 2019/04/06
   * @return Integer
   * */
   public Integer delete(List<Integer> sg_id_list,String sgsqr);
  
   /*
    * 保存或者修改宿舍管理
    * data 2019/04/06
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Ssgl ssgl);
  
    /*
	 * 修改确认转态
	 * return String
	 * data 2019/04/06
	 * */
	Integer qrstatus(List<Integer> sg_id_list, String qjshr);
	
	
	
	
}
