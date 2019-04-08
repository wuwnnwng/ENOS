package com.wwn.service;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import com.wwn.jpa.entity.Xxfb;
public interface XxfbService {
	 /**
	 * 信息发布列表(分页加模糊)
	 * data 2019/03/28
	 * @return Page<Xxfb>
	 */
    public Page<Xxfb> find(Integer page, Integer size,String field);
   /*
   * 根据用户的id 删除用户信息
   * data 2019/03/28
   * @return Integer
   * */
   public Integer deleteXxfb(List<Integer> xf_id_list,String ggfbr);
   /*
    * 保存或者修改信息发布信息
    * data 2019/03/26
    * @return Map<String,Object>
    * */
   public Map<String,Object> save(Xxfb xxfb);
    
	
}
