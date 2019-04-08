package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wwn.jpa.entity.Wcgl;

@Repository
public interface WcglRepository extends CrudRepository<Wcgl, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/04
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_wcgl  where wc_id in (?1) and wcr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> wc_id_list,String wcr);
	
	/*
     *  分页加模糊匹配
     * return  List<wcgl> list 
     * date 2019/04/04
     * */
	 @Query(value = "SELECT t.wc_id,t.wcr,t.wcshr,t.wcksrq,t.wcjsrq,t.wcshrq,t.wcsy,t.wcglxm,t.wcrbmmc ,t.wcshzt FROM t_wcgl t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.wcr  like  %?1%, 1=1 ) and   "
		 		+ "if(?2!=null || ?2!='',t.wcrbmmc like %?2%, 1=1) and   "
		 		+ "if(?3!=null || ?3!='',t.wcglxm like %?3%, 1=1) "
		 		+ " and if(?4!=null || ?4!='',t.wcshzt like %?4%, 1=1) "
		 		+ "and if(?5!=null || ?5!='',t.wcksrq like %?5%, 1=1) "
		 		+ "and if(?6!=null || ?6!='',t.wcjsrq like %?6%, 1=1)",
		 	   countQuery = "SELECT count(*) FROM t_wcgl t WHERE   "
		 	   		+ "if(?1!=null ||  ?1!='',t.wcr  like  %?1%, 1=1 )"
		 	   		+ "  and   if(?2!=null || ?2!='',t.wcrbmmc like %?2%, 1=1) "
		 	   		+ "and if(?3!=null || ?3!='',t.wcglxm like %?3%, 1=1) "
		 	   		+ "and if(?4!=null || ?4!='',t.wcshzt like %?4%, 1=1) "
		 	   		+ " and if(?5!=null || ?5!='',t.wcksrq like %?5%, 1=1) "
		 	   		+ "and if(?6!=null || ?6!='',t.wcjsrq like %?6%, 1=1) "  ,nativeQuery = true)
	Page<Wcgl>   find( String wcr ,String wcrbmmc ,String wcglxm,String wcshzt,String wcksrq,String wcjsrq,Pageable pageable );
 
	 
	 /*
	 * 修改审核状态
	 * return String
	 *date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_wcgl  t SET t.wcshzt='已审核',t.wcshrq=CURDATE() where wc_id in (?1)",nativeQuery = true)
	public Integer qrstatus(List<Integer> wc_id ) ;
	
	
	
	
}
