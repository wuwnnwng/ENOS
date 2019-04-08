package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwn.jpa.entity.Xmps;


@Repository
public interface XmpsRepository extends CrudRepository<Xmps, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/03
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_xmps  where xp_id in (?1) and xpr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> xp_id_list,String xpr);
	
	/*
     *  分页加模糊匹配
     * return  List<xmps> list 
     * date 2019/04/02
     * */
	 @Query(value = "SELECT t.xp_id,t.xpxmmc,t.xpmkmc,t.xpyj,t.xprq,t.xpr,t.xpbz  FROM t_xmps t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.xpxmmc  like  %?1%, 1=1 ) and   if(?2!=null || ?2!='',t.xpr like %?2%, 1=1) and   if(?3!=null || ?3!='',t.xprq like %?3%, 1=1)   ",
		 	   countQuery = "SELECT count(*) FROM t_xmps t WHERE    if(?1!=null ||  ?1!='',t.xpxmmc  like  %?1%, 1=1 ) and   if(?2!=null || ?2!='',t.xpr like %?2%, 1=1) and   if(?3!=null || ?3!='',t.xprq like %?3%, 1=1)   "  ,nativeQuery = true)
	Page<Xmps>   find( String xpxmmc ,String xpr ,String xprq ,Pageable pageable );
 
	 
	
	
	
}
