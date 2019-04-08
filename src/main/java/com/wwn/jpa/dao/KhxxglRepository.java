package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Khxxgl;


@Repository
public interface KhxxglRepository extends CrudRepository<Khxxgl, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/06
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_khxxgl  where kh_id in (?1) and khcjr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> kh_id_list,String khcjr);
	
	/*
     *  分页加模糊匹配
     * return  List<khxxgl> list 
     * date 2019/04/06
     * */
	 @Query(value = "SELECT t.kh_id,t.khname,t.khcjr,t.khxb,t.khdwmc,t.khyx,t.khdh,t.khdz,t.khhzkssq ,t.khhzjsrq ,t.khdjxm ,t.khhzzt ,t.khbz  FROM t_khxxgl t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.khname  like  %?1%, 1=1 ) and   "
		 		+ "if(?2!=null || ?2!='',t.khhzzt like %?2%, 1=1) and   "
		 		+ "if(?3!=null || ?3!='',t.khhzkssq like %?3%, 1=1) "
		 		+ " and if(?4!=null || ?4!='',t.khhzjsrq like %?4%, 1=1) ",
		 	   countQuery = "SELECT count(*) FROM t_khxxgl t WHERE   "
		 	   		+ "if(?1!=null ||  ?1!='',t.khname  like  %?1%, 1=1 )"
		 	   		+ "  and   if(?2!=null || ?2!='',t.khhzzt like %?2%, 1=1) "
		 	   		+ "and if(?3!=null || ?3!='',t.khhzkssq like %?3%, 1=1) "
		 	   		+ "and if(?4!=null || ?4!='',t.khhzjsrq like %?4%, 1=1) " ,nativeQuery = true)
	Page<Khxxgl>   find( String khname ,String khhzzt ,String khhzkssq,String khhzjsrq ,Pageable pageable );
 
	 
	
}
