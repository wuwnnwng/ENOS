package com.wwn.service;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Wdtx;
public interface WdtxService {
	 /**
	 * 用户列表(分页加模糊)
	 * data 2019/03/26
	 * @return Page<TPerson>
	 */
    public Page<Wdtx> find(Integer page, Integer size,String field,String dlzh);
   /*
   * 根据用户的id 删除用户信息
   * data 2019/03/26
   * @return Integer
   * */
   public Integer delete(List<Integer> wt_id_list,String fbr);
   /*
    * 保存或者修改用户信息
    * data 2019/03/26
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Wdtx wdtx);
  
    /*
	 * 修改确认转态
	 * return String
	 * date 2019/03/26
	 * */
	public Integer qrstatus(List<Integer> wt_id );
	
	/*
	 * 等到员工新姓名
	 * Map<String ,Object> 
	 * date 2019/03/27
	 * */
	public List<String> getUserName();
	
	
	
	
	
}
