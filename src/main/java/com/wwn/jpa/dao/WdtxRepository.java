package com.wwn.jpa.dao;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wwn.jpa.entity.Wdtx;
@Repository
public interface WdtxRepository extends CrudRepository<Wdtx, Integer>   {
	/*
	 * 按照条件（id） 删除数据
	 * return Integer
	 * date 2019/03/26
	 * */
	@Transactional
    @Modifying
    @Query(value = "delete from t_wdtx  where wt_id in (?1) and fbr=?2 ",nativeQuery = true)
	Integer deleteWdtx(List<Integer> wt_id_list,String fbr);
	/*
     *  分页加模糊匹配
     * return  List<Wdtx> list 
     * date 2019/03/26
     * */
//	 @Cacheable
	 @Query(value = "SELECT t.wt_id,t.fbr,t.laiyuan,t.riqi,t.shixiang,t.`status`,t.stadec,t.zdr FROM t_wdtx t"+
	 		" WHERE (t.fbr=?2 or t.zdr=?2 ) and if(?1 !='',(t.laiyuan like %?1% or "
	 		+ " t.shixiang like %?1% or t.fbr like %?1% or t.zdr like %?1% "
	 		+ " or t.stadec like %?1%),1=1)", countQuery = "SELECT count(*) FROM t_wdtx t WHERE "
	 	 	+ "(t.fbr=?2 or t.zdr=?2 ) and if(?1 !='',(t.laiyuan like %?1% or "
	 	 	+ "t.shixiang like %?1% or t.fbr like %?1% or "
	 	 	+ "t.zdr like %?1% or t.stadec like %?1%),1=1) ",nativeQuery = true)
	 Page<Wdtx>   find(String field,String dlzh,Pageable pageable );
	 
	 /*
	 * 修改确认转态
	 * return String
	 * date 2019/03/26
	 * */
	@Transactional
    @Modifying
    @Query(value = "UPDATE   t_wdtx  t SET t.status=1  ,t.stadec='已确认' where wt_id in (?1)",nativeQuery = true)
	public Integer qrstatus(List<Integer> wt_id ) ;
	
	
	
	
}
