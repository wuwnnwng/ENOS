package com.wwn.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wwn.jpa.entity.Sbdk;
@Repository
public interface SbdkRepository extends CrudRepository<Sbdk, Integer>   {
	 
	/*
     *  返回当天的记录
     * return  List<Gzjl> list 
     * date 2019/03/26
     * */
//	 @Cacheable
	 @Query(value = "select t.dk_id,t.dk_name,t.dk_depart,t.dk_kssj,t.dk_jssj  from  t_dk"
	 		+ " t where  to_days(t.dk_kssj) =to_days(now()) and t.dk_name=?1 LIMIT 1",nativeQuery = true)
	 List<Sbdk>   find(String dk_name );
	 
	 
	 
 
}
