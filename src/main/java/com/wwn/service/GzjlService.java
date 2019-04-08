package com.wwn.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.wwn.jpa.entity.Gzjl;
public interface GzjlService {
	 /**
	 * 信息发布列表(分页加模糊)
	 * data 2019/03/30
	 * @return Page<Xxfb>
	 */
    public Page<Gzjl> find(Integer page, Integer size,Gzjl gzjl);
   /*
   * 根据用户的id 删除工作记录信息 
   * data 2019/03/30
   * @return Integer
   * */
   public Integer delete(List<Integer> gj_id_list,String jlrxm);
   /*
    * 保存或者修改工作记录信息 
    * data 2019/03/30
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Gzjl gzjl);
    
	
}
