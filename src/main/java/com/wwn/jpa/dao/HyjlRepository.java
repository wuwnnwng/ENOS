package com.wwn.jpa.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wwn.jpa.entity.Hyjl;
@Repository
public interface HyjlRepository extends CrudRepository<Hyjl, Integer>   {
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/31
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_hyjl  where hj_id in (?1) and hyjlrxm=?2 ",nativeQuery = true)
	Integer delete(List<Integer> hj_id_list,String hyjlrxm);
	
	/*
     *  分页加模糊匹配
     * return  List<Hyjl> list 
     * date 2019/03/26
     * */
//	 @Cacheable
//	 @Query(value = "SELECT t.hj_id,t.hyjlrxm,t.hyyt,t.hyly,t.hynr,t.hyjlun,t.hyrq,t.hyxmmc,t.hybmmc,t.hylyrxm  ,t.hycyry FROM t_hyjl t"+
//	 		" WHERE  if(?1!=null || ?1!='',t.hybmmc like %?1%  ,1=1) and if(?2!=null || ?2!='',t.hyxmmc like %?2%  ,1=1)"
//	 		+ " and if(?3!=null || ?3!='',t.hycyry like %?3%  ,1=1) and if(?4!=null || ?4!='',t.hyyt like %?4%  ,1=1) "
//	 		+ " and if(?5!=null || ?5!='',t.hyjlrxm like %?5%  ,1=1) ",
//	 		countQuery = "SELECT count(*) FROM t_hyjl t "+
//	 				" WHERE  if(?1!=null || ?1!='',t.hybmmc like %?1%  ,1=1) and if(?2!=null || ?2!='',t.hyxmmc like %?2%  ,1=1)"
//	 		 		+ " and if(?3!=null || ?3 !='',t.hycyry like %?3%  ,1=1) and if(?4!=null || ?4!='',t.hyyt like %?4%  ,1=1) "
//	 		 		+ "  and if(?5!=null || ?5!='',t.hyjlrxm like %?5%  ,1=1) ",nativeQuery = true)
//	 Page<Hyjl>   find(String hybmmc,String hyxmmc,String hycyry,String hyyt ,Date hyrq,String hyjlrxm,Pageable pageable );
//	 Page<Hyjl>   find(String hybmmc,String hyxmmc,String hycyry,String hyyt  ,String hyjlrxm,Pageable pageable );
//	 and if(?5!=null || ?5!='',t.hyrq like %?5%  ,1=1)
	 @Query(value = "SELECT t.hj_id,t.hyjlrxm,t.hyyt,t.hyly,t.hynr,t.hyjlun,t.hyrq,t.hyxmmc,t.hybmmc,t.hylyrxm  ,t.hycyry FROM t_hyjl t"+
		 		" WHERE  if(?1!=null || ?1!='',t.hybmmc like %?1%,1=1 ) and   if(?2!=null || ?2!='',t.hyxmmc like %?2%,1=1 )"
		 		+ " and if(?3!=null || ?3!='',t.hyjlrxm like %?3%,1=1 ) and if(?4!=null || ?4!='',t.hycyry like %?4%,1=1 ) "
		 		+ "and if(?5!=null || ?5!='',t.hyyt like %?5%,1=1 ) and if(?6!=null || ?6!='',t.hyrq like %?6%,1=1 )",
		 		countQuery = "SELECT count(*) FROM t_hyjl t "+
		 				" WHERE  if(?1!=null || ?1!='',t.hybmmc like %?1%  ,1=1) and if(?2!=null || ?2!='',t.hyxmmc like %?2%,1=1 )"
		 				+ " and if(?3!=null || ?3!='',t.hyjlrxm like %?3%,1=1 )  and if(?4!=null || ?4!='',t.hycyry like %?4%,1=1 )"
		 				+ "and if(?5!=null || ?5!='',t.hyyt like %?5%,1=1 ) and if(?6!=null || ?6!='',t.hyrq like %?6%,1=1 ) ",nativeQuery = true)
//	 @Query(value = "SELECT t.hj_id,t.hyjlrxm,t.hyyt,t.hyly,t.hynr,t.hyjlun,t.hyrq,t.hyxmmc,t.hybmmc,t.hylyrxm  ,t.hycyry FROM t_hyjl t"+
//		 		" WHERE  if(?1!=null || ?1!='',t.hycyry like %?1%,1=1 )",
//		 		countQuery = "SELECT count(*) FROM t_hyjl t "+
//		 				" WHERE  if(?1!=null || ?1!='',t.hycyry like %?1%  ,1=1)",nativeQuery = true)
	Page<Hyjl>   find(String hybmmc,String hyxmmc,String hyjlrxm, String hycyry,String hyyt, String hyrq, Pageable pageable );
	 
	 
 
}
