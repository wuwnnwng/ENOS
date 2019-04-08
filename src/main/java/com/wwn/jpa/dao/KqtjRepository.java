package com.wwn.jpa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wwn.jpa.entity.Kqtj;
@Repository
public interface KqtjRepository extends JpaRepository<Kqtj, Integer>, CrudRepository<Kqtj, Integer>{
	
//   @Cacheable 
//   @Query(value ="SELECT  t.dk_id, t.dkname,t.dkdepart,t.rq,t.qdsj,t.qtsj,t.xq,t.gzsj,t.cdsj,t.ztsj,t.zlsj,"
//   		+ "t.wtsj from  v_kqtj t where  YEAR(str_to_date(t.rq, '%Y')) =?1"
//   		+ " and MONTH(str_to_date(t.rq, '%m'))=?2 and t.dkname=?3 ",nativeQuery = true )
	@Query(value ="SELECT t.sbkzt, t.xbkzt, t.dk_id, t.dkname,t.dkdepart,t.rq,t.qdsj,t.qtsj,t.xq,t.gzsj,t.cdsj,t.ztsj,t.zlsj,"
	   		+ "t.wtsj from  v_kqtj t where  YEAR(str_to_date(t.rq, '%Y')) =?1 and MONTH(str_to_date(t.rq, '%Y-%m-%d'))=?2 and t.dkname=?3 ",
	   	  countQuery = "SELECT count(*) FROM v_kqtj t where  YEAR(str_to_date(t.rq, '%Y')) =?1   and MONTH(str_to_date(t.rq, '%Y-%m-%d'))=?2 and t.dkname=?3 ",	nativeQuery = true )
   Page<Kqtj>  find(String year,String month,String dlzh,Pageable pageable);
   
   
   
//   WHERE
//	(
//		(MONTH(`kqtjbb`.`rq`) = 4)
//		AND (YEAR(`kqtjbb`.`rq`) = 2019)
//		AND (
//			`kqtjbb`.`dkname` = '吴万能'
//		)
//	)
}
