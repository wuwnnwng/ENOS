package com.wwn.service;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import com.wwn.jpa.entity.Hyjl;
public interface HyjlService {
	
	/**
	 * 会议记录(分页加模糊)
	 * data 2019/03/31
	 * @return Page<Xxfb>
	 */
    public Page<Hyjl> find(Integer page, Integer size,Hyjl hyjl);
   /*
   * 根据用户的id 删除工作记录信息 
   * data 2019/03/301
   * @return Integer
   * */
   public Integer delete(List<Integer> hj_id_list,String hyjlrxm);
   
   /*
    * 保存或者修改工作记录信息 
    * data 2019/03/31
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Hyjl hyjl);
    
	
}
