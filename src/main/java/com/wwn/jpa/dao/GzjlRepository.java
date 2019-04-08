package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Gzjl;
@Repository
public interface GzjlRepository extends CrudRepository<Gzjl, Integer>   {
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/30
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_gzjl  where gj_id in (?1) and jlrxm=?2 ",nativeQuery = true)
	Integer delete(List<Integer> wt_id_list,String jlrxm);
	/*
     *  分页加模糊匹配
     * return  List<Gzjl> list 
     * date 2019/03/26
     * */
//	 @Cacheable
	 @Query(value = "SELECT t.gzshrq,t.gj_id,t.pjjg,t.pjly,t.jlrxm,t.gzrq,t.gznr,t.gzwcqk,t.xmmc,t.bmmc,t.psrxm FROM t_gzjl t"+
	 		" WHERE  if(?1!=null || ?1 !='',t.bmmc like %?1%  ,1=1) and if(?2!=null || ?2!='',t.xmmc like %?2%  ,1=1)"
	 		+ " and if(?3!=null || ?3 !='',t.jlrxm like %?3%  ,1=1) and if(?4!=null || ?4!='',t.gznr like %?4%  ,1=1)",
	 		countQuery = "SELECT count(*) FROM t_gzjl t WHERE "
	 				+" if(?1!=null || ?1!='',t.bmmc like %?1% ,1=1) and if(?2!=null || ?2!='',t.xmmc like %?2%  ,1=1)"
	 		 		+ " and if(?3!=null || ?3 !='',t.jlrxm like %?3% ,1=1) and if(?4!=null || ?4!='',t.gznr like %?4% ,1=1)",nativeQuery = true)
	 Page<Gzjl>   find(String bmmc,String xmmc,String jlrxm,String gznr ,Pageable pageable );
	 
	 
	 
 
}
