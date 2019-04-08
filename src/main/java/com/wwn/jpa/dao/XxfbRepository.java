package com.wwn.jpa.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wwn.jpa.entity.Xxfb;
@Repository
public interface XxfbRepository extends CrudRepository<Xxfb, Integer>   {
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/26
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_xxfb  where xf_id in (?1) and ggfbr=?2 ",nativeQuery = true)
	Integer deleteXxfb(List<Integer> wt_id_list,String fbr);
	/*
     *  分页加模糊匹配
     * return  List<Wdtx> list 
     * date 2019/03/26
     * */
//	 @Cacheable
	 @Query(value = "SELECT t.xf_id,t.ggbt,t.ggnr,t.ggfj,t.fbrq,t.ggfbr FROM t_xxfb t"+
	 		" WHERE  if(?1 !='',(t.ggbt like %?1% or "
	 		+ " t.ggnr like %?1% or t.ggfbr like %?1% ),1=1 )", 
	 		countQuery = "SELECT count(*) FROM t_xxfb t WHERE "
	 	 	+ "  if(?1 !='',(t.ggbt like %?1% or "
	 	 	+ "t.ggnr like %?1% or t.ggfbr like %?1% ) ,1=1) ",nativeQuery = true)
	 Page<Xxfb>   find(String field,Pageable pageable );
	 
	
	/*
     * 查询附件是否存在，如果存在不需要再次上传
     * return String
     * wwn
     * date 2019/03/29
     * */
	 @Query(value = "SELECT t.ggfj from t_xxfb t where t.xf_id=?1",nativeQuery = true)
	 String  findFj( Integer xf_id);
		 
		
	
}
