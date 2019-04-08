package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Ryqk;

@Repository
public interface RyqkRepository extends CrudRepository<Ryqk, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/03
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_ryqk  where rq_id in (?1) and rqzprxm=?2 ",nativeQuery = true)
	Integer delete(List<Integer> rq_id_list,String rqzprxm);
	
	/*
     *  分页加模糊匹配
     * return  List<ryqk> list 
     * date 2019/04/02
     * */
	 @Query(value = "SELECT t.rq_id,t.rqcxxm,t.rqzprxm,t.rqbz,t.rqjslb,t.rqksrq,t.rqxm,t.rqyjcxsj,t.rqbmmc FROM t_ryqk t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.rqcxxm  like  %?1%, 1=1 ) and   if(?2!=null || ?2!='',t.rqbmmc like %?2%, 1=1) and   if(?3!=null || ?3!='',t.rqxm like %?3%, 1=1)  and if(?4!=null || ?4!='',t.rqjslb like %?4%, 1=1) and if(?5!=null || ?5!='',t.rqksrq like %?5%, 1=1)",
		 	   countQuery = "SELECT count(*) FROM t_ryqk t WHERE   if(?1!=null ||  ?1!='',t.rqcxxm  like  %?1%, 1=1 ) and   if(?2!=null || ?2!='',t.rqbmmc like %?2%, 1=1) and   if(?3!=null || ?3!='',t.rqxm like %?3%, 1=1)  and if(?4!=null || ?4!='',t.rqjslb like %?4%, 1=1) and if(?5!=null || ?5!='',t.rqksrq like %?5%, 1=1)"  ,nativeQuery = true)
	Page<Ryqk>   find( String rqcxxm ,String rqbmmc ,String rqxm,String rqjslb,String rqksrq,Pageable pageable );
 
	 
	 /*
	 * 修改审核转态转态
	 * return String
	 *date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_ryqk  t SET t.xdshzt='已审核',t.xdshsj=CURDATE() where xd_id in (?1)",nativeQuery = true)
	public Integer qrstatus(List<Integer> xd_id ) ;
	
	
	
	
}
