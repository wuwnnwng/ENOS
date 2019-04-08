package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Ssgl;

@Repository
public interface SsglRepository extends CrudRepository<Ssgl, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/06
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_ssgl  where sg_id in (?1) and sgsqr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> sg_id_list,String sgsqr);
	
	/*
     *  分页加模糊匹配
     * return  List<ssgl> list 
     * date 2019/04/06
     * */
	 @Query(value = "SELECT t.sg_id,t.sgsqr,t.sgjbr,t.sgksrq,t.sgjsrq,t.sgssdz,t.sgjbzt,t.sgbz,t.sgssmc  FROM t_ssgl t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.sgsqr  like  %?1%, 1=1 ) and   "
		 		+ "if(?2!=null || ?2!='',t.sgjbr like %?2%, 1=1) and   "
		 		+ "if(?3!=null || ?3!='',t.sgssdz like %?3%, 1=1) "
		 		+ " and if(?4!=null || ?4!='',t.sgssmc like %?4%, 1=1) "
		 		+ "and if(?5!=null || ?5!='',t.sgksrq like %?5%, 1=1) "
		 		+ "and if(?6!=null || ?6!='',t.sgjsrq like %?6%, 1=1)"
		 		+ "and if(?7!=null || ?7!='',t.sgjbzt like %?7%, 1=1) ",
		 	   countQuery = "SELECT count(*) FROM t_ssgl t WHERE   "
		 	   		+ "if(?1!=null ||  ?1!='',t.sgsqr  like  %?1%, 1=1 )"
		 	   		+ "  and   if(?2!=null || ?2!='',t.sgjbr like %?2%, 1=1) "
		 	   		+ "and if(?3!=null || ?3!='',t.sgssdz like %?3%, 1=1) "
		 	   		+ "and if(?4!=null || ?4!='',t.sgssmc like %?4%, 1=1) "
		 	   		+ " and if(?5!=null || ?5!='',t.sgksrq like %?5%, 1=1) "
		 	   		+ "and if(?6!=null || ?6!='',t.sgjsrq like %?6%, 1=1)"
		 	   		+ "and if(?7!=null || ?7!='',t.sgjbzt like %?7%, 1=1) "  ,nativeQuery = true)
	Page<Ssgl>   find( String sgsqr ,String sgjbr ,String sgssdz,String sgssmc,String sgksrq,String sgjsrq,String sgjbzt,Pageable pageable );
 
	 
	 /*
	 * 修改审核状态
	 * return String
	 *date 2019/04/06
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_ssgl  t SET t.sgjbzt='已批准' ,t.sgjbr=?2  where sg_id in (?1) ",nativeQuery = true)
	public Integer qrstatus(List<Integer> sg_id ,String sgjbr) ;
	
	
	
	
}
