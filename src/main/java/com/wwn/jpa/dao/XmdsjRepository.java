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
import com.wwn.jpa.entity.Xmdsj;

@Repository
public interface XmdsjRepository extends CrudRepository<Xmdsj, Integer>   {
	
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/04/02
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_xmdsj  where xd_id in (?1) and xdjlr=?2 ",nativeQuery = true)
	Integer delete(List<Integer> xd_id_list,String xdjlr);
	
	/*
     *  分页加模糊匹配
     * return  List<Xmdsj> list 
     * date 2019/04/02
     * */
//	 @Query(value = "SELECT t.xd_id,t.xdjlr,t.xdshr,t.xdshsj,t.xdrq,t.xdnr,t.xdxmmc,t.xdstatus,t.xdshzt  FROM t_xmdsj t "+
//		 		" WHERE   if(?1!=null ||  ?1!='',t.xdxmmc  like  %?1%, 1=1 )   and   if(?2!=null || ?2!='',t.xdjlr like %?2%, 1=1)  and   if(?3!=null || ?3!='',t.xdshr like %?3%, 1=1)  and   if(?4!=null || ?4!='',t.xdshzt like %?4%, 1=1 and   if(?5!=null || ?5!='',t.xdrq like %?5%, 1=1))",
//		 	   countQuery = "SELECT count(*) FROM t_xmdsj t WHERE    if(?1!=null ||  ?1!='',t.xdxmmc  like  %?1%, 1=1 )   and   if(?2!=null || ?2!='',t.xdjlr like %?2%, 1=1)  and   if(?3!=null || ?3!='',t.xdshr like %?3%, 1=1)  and   if(?4!=null || ?4!='',t.xdshzt like %?4%, 1=1) and   if(?5!=null || ?5!='',t.xdrq like %?5%, 1=1) "  ,nativeQuery = true)
	 @Query(value = "SELECT t.xd_id,t.xdjlr,t.xdshr,t.xdshsj,t.xdrq,t.xdnr,t.xdxmmc,t.xdstatus,t.xdshzt  FROM t_xmdsj t "+
		 		" WHERE   if(?1!=null ||  ?1!='',t.xdxmmc  like  %?1%, 1=1 ) and   if(?2!=null || ?2!='',t.xdjlr like %?2%, 1=1) and   if(?3!=null || ?3!='',t.xdshzt like %?3%, 1=1)  and if(?4!=null || ?4!='',t.xdrq like %?4%, 1=1) and if(?5!=null || ?5!='',t.xdshr like %?5%, 1=1)",
		 	   countQuery = "SELECT count(*) FROM t_xmdsj t WHERE   if(?1!=null ||  ?1!='',t.xdxmmc  like  %?1%, 1=1 )  and   if(?2!=null || ?2!='',t.xdjlr like %?2%, 1=1) and if(?3!=null || ?3!='',t.xdshzt like %?3%, 1=1) and if(?4!=null || ?4!='',t.xdrq like %?4%, 1=1)  and if(?5!=null || ?5!='',t.xdshr like %?5%, 1=1) "  ,nativeQuery = true)
	Page<Xmdsj>   find( String xdxmmc ,String xdjlr ,String xdshzt,String xdrq,String xdshr,Pageable pageable );
 
	 
	 /*
	 * 修改审核转态转态
	 * return String
	 *date 2019/04/01
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_xmdsj  t SET t.xdshzt='已审核',t.xdshsj=CURDATE() where xd_id in (?1)",nativeQuery = true)
	public Integer qrstatus(List<Integer> xd_id ) ;
	
	
	
	
}
