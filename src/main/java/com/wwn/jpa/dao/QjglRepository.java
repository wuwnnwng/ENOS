package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Qjgl;

@Repository
public interface QjglRepository extends CrudRepository<Qjgl, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/04
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_qjgl  where qj_id in (?1) and qjr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> qj_id_list,String qjr);
	
	/*
     *  分页加模糊匹配
     * return  List<qjgl> list 
     * date 2019/04/04
     * */
	 @Query(value = "SELECT t.qj_id,t.qjr,t.qjshr,t.qjksrq,t.qjjsrq,t.qjshrq,t.qjsy,t.qjlx,t.qjrbmmc ,t.qjshzt ,t.qjrbz FROM t_qjgl t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.qjr  like  %?1%, 1=1 ) and   "
		 		+ "if(?2!=null || ?2!='',t.qjrbmmc like %?2%, 1=1) and   "
		 		+ "if(?3!=null || ?3!='',t.qjlx like %?3%, 1=1) "
		 		+ " and if(?4!=null || ?4!='',t.qjshzt like %?4%, 1=1) "
		 		+ "and if(?5!=null || ?5!='',t.qjksrq like %?5%, 1=1) "
		 		+ "and if(?6!=null || ?6!='',t.qjjsrq like %?6%, 1=1)",
		 	   countQuery = "SELECT count(*) FROM t_qjgl t WHERE   "
		 	   		+ "if(?1!=null ||  ?1!='',t.qjr  like  %?1%, 1=1 )"
		 	   		+ "  and   if(?2!=null || ?2!='',t.qjrbmmc like %?2%, 1=1) "
		 	   		+ "and if(?3!=null || ?3!='',t.qjlx like %?3%, 1=1) "
		 	   		+ "and if(?4!=null || ?4!='',t.qjshzt like %?4%, 1=1) "
		 	   		+ " and if(?5!=null || ?5!='',t.qjksrq like %?5%, 1=1) "
		 	   		+ "and if(?6!=null || ?6!='',t.qjjsrq like %?6%, 1=1) "  ,nativeQuery = true)
	Page<Qjgl>   find( String qjr ,String qjrbmmc ,String qjlx,String qjshzt,String qjksrq,String qjjsrq,Pageable pageable );
 
	 
	 /*
	 * 修改审核状态
	 * return String
	 *date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_qjgl  t SET t.qjshzt='已审核',t.qjshrq=CURDATE(),t.qjshr=?2  where qj_id in (?1) ",nativeQuery = true)
	public Integer qrstatus(List<Integer> qj_id ,String qjshr) ;
	
	
	
	
}
