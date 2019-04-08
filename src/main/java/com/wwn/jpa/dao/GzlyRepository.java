package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Gzly;
@Repository
public interface GzlyRepository extends CrudRepository<Gzly, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_gzly  where gl_id in (?1) and fjr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> gl_id_list,String fjr);
	
	/*
     *  分页加模糊匹配
     * return  List<Gzly> list 
     * date 2019/04/01
     * */
//	 @Query(value = "SELECT t.gl_id,t.fjr,t.fjrq,t.qrrq,t.sjr,t.lynr,t.lystatus  FROM t_gzly t "+
//		 		" WHERE (t.fjr=?4 or t.sjr=?4 )   and   if(?1!=null ||  ?1!='',t.fjr  like  %?1%, 1=1 ) and if(?2 !=null||?2!='',t.lystatus like %?2%, 1=1) and  if(?3!=null || ?3!='',t.fjrq like %?3%,1=1 ) ",
//		 	   countQuery = "SELECT count(*) FROM t_gzly t WHERE  (t.fjr=?4 or t.sjr=?4 )   and  if(?1!=null ||  ?1!='',t.fjr  like  %?1% , 1=1 ) and if(?2 !=null||?2!='',t.lystatus like %?2%, 1=1) and   if(?3!=null || ?3!='',t.fjrq like %?3%, 1=1)"  ,nativeQuery = true)
//	Page<Gzly>   find( String fjr,String  lystatus,String fjrq, String dlzh,Pageable pageable );
	 @Query(value = "SELECT t.gl_id,t.fjr,t.fjrq,t.qrrq,t.sjr,t.lynr,t.lystatus  FROM t_gzly t "+
		 		" WHERE (t.fjr=?4 or t.sjr=?4 )   and   if(?1!=null ||  ?1!='',t.fjr  like  %?1%, 1=1 )   and   if(?2!=null || ?2!='',t.fjrq like %?2%, 1=1)  and   if(?3!=null || ?3!='',t.lystatus like %?3%, 1=1)  ",
		 	   countQuery = "SELECT count(*) FROM t_gzly t WHERE  (t.fjr=?4 or t.sjr=?4 )   and  if(?1!=null ||  ?1!='',t.fjr  like  %?1% , 1=1 )  and   if(?2!=null || ?2!='',t.fjrq like %?2%, 1=1) and   if(?3!=null || ?3!='',t.lystatus like %?3%, 1=1) "  ,nativeQuery = true)
	 Page<Gzly>   find( String fjr, String fjrq, String  lystatus,String dlzh,Pageable pageable );
 
	 
	 /*
	 * 修改确认转态
	 * return String
	 *date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_gzly  t SET t.lystatus='已确认',t.qrrq=CURDATE() where gl_id in (?1)",nativeQuery = true)
	public Integer qrstatus(List<Integer> gl_id ) ;
	
	
	
	
}
