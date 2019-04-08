package com.wwn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.wwn.jpa.dao.QjlxRepository;
import com.wwn.jpa.dao.TPersonRepository;
import com.wwn.jpa.dao.UserInfoRepository;
import com.wwn.jpa.dao.WdtxRepository;
import com.wwn.jpa.entity.Qjlx;
import com.wwn.jpa.entity.TPerson;
import com.wwn.mybatis.dao.TPersonMapper;
import com.wwn.service.TPersonService;
import com.wwn.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnosApplicationTests {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private TPersonMapper tPersonMapper;
	@Autowired
	private TPersonService tPersonService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	WdtxRepository wdtxRepository;
	@Autowired     
	TPersonRepository tPersonRepository;
	@Autowired    
	QjlxRepository qjlxrepository;
	
	@Test
	public void contextLoads() {
		System.out.println("hello world");
	}
	/*
	 * springboot 整合jpa测试
	 * */
	@Test
	public void testJpa() {
		 System.out.println(userInfoRepository.findByusername("管理员"));
	}
	/*
	 * JpaRepository测试分页
	 * */
	@Test
	public void testJpaRepositoryPage() {
		Integer page = 0;
		Integer size = 5;
		Page<com.wwn.jpa.entity.TPerson> tPerson = tPersonService.find(page, size);
		System.out.println("总页数:"+tPerson.getTotalPages());
		System.out.println("数据总数:"+tPerson.getTotalElements());
		System.out.println("本页数据条数:"+tPerson.getNumberOfElements());
		System.out.println("当前页序号:"+tPerson.getNumber());
		System.out.println("每页长度:"+tPerson.getSize());
		System.out.println("是否最后一页:"+tPerson.isLast());
		System.out.println("是否首页:"+tPerson.isFirst());
		System.out.println("排序:"+tPerson.getSort());
		System.out.println("数据列表:"+tPerson.getContent());
//		{
//		    "content": [{}], // 数据列表
//		    "last": true, // 是否最后一页
//		    "totalPages": 1, // 总页数
//		    "totalElements": 1, // 数据总数
//		    "sort": null, // 排序
//		    "first": true, // 是否首页
//		    "numberOfElements": 1, // 本页数据条数
//		    "size": 10, // 每页长度
//		    "number": 0 // 当前页序号
//		}
	}
	/*
	 * springboot 整合mybatis测试
	 * */
//	@Test
//	public void testMybatis() {
//		List<TPerson> list = new ArrayList<TPerson>();
//		list = tPersonMapper.selectAll();
//		for (TPerson tPerson : list) {
//			System.out.println(tPerson);
//		}
//	}
	/*
	 *  测试批量删除
	 * */
	@Test
	public void testDeletePerson() {
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(22);
//		pidList.add(2);
//		pidList.add(4);
		Integer result = tPersonService.deletePerson(pidList);
		System.out.println(result);
	}
	/*
	 *  测试保存和修改
	 * */
	@Test
	public void testSavePerson() {
		Map<String,Object> map = new  HashMap<String, Object>();
		TPerson tPerson = new TPerson();
		tPerson.setPid(2);
		tPerson.setPage(30);
		tPerson.setPname("王五");
		Map<String, Object> save = tPersonService.save(tPerson);
		System.out.println(save);
	}
	/*
	 *  测试保存和修改
	 * */
	@Test
	public void searchPerson() {
		String field = "20  or  1=1";
		List<TPerson> list = new ArrayList<TPerson>();
		Integer page = 0 ;
		Integer size = 8;
		list = tPersonService.searchPerson(field, page ,size);
		System.out.println(list);
	}
	/*
	 *  测试查询总记录数
	 * */
	@Test
	public void searchCount() {
		String field = "2";
		Integer page = 0;
		Integer size = 8;
		Integer count = tPersonService.getPersonCount(field,page,size);
		System.out.println(count);
	}
	/*
	 *  测试用户是否存在
	 * */
	@Test
	public void checkUser() {
		String name = "吴万能"; 
		String username = userInfoService.checkUser(name);
		System.out.println(username);
	}
	/*
	 *  测试分页加模糊查询
	 *  
	 * */
	@Test
	public void checPagMf() {
		Page<TPerson> find = tPersonService.find(0, 8);
		System.out.println("总页数:"+find.getTotalPages());
		System.out.println("数据总数:"+find.getTotalElements());
		System.out.println("本页数据条数:"+find.getNumberOfElements());
		System.out.println("当前页序号:"+find.getNumber());
		System.out.println("每页长度:"+find.getSize());
		System.out.println("是否最后一页:"+find.isLast());
		System.out.println("是否首页:"+find.isFirst());
		System.out.println("排序:"+find.getSort());
		System.out.println("数据列表:"+find.getContent());
		System.out.println(find);
	}
	
	/*
	 *  测试查询用户
	 * */
	@Test
	public void testSearchUser() {
//	  UserInfo uf = new UserInfo();
//	  uf.setName("王岩");
//	  uf.setPassword("f329423e5500e909a6bb7bd631550f7e");
//	  uf.setSalt("manager");
//	  uf.setUsername("manager");
//	  UserInfo save = userInfoRepository.save(uf);
//	  System.out.println(save);
	  Map<String, Object> wjmm = userInfoService.wjmm("吴万能", "12345678");
	  System.out.println(wjmm);	 
//	 Integer in = userInfoRepository.xgmm( "吴万能","a66abb5684c45962d887564f08346e8d");
//	 System.out.println(in);a66abb5684c45962d887564f08346e8d 1234546
//	  Depart depart = new  Depart();
//	  depart.
	}
	/*
	 *  测试查询用户
	 * */
	@Test
	public void testGetUserName() {
	    List<String> userName = userInfoRepository.getUserName();
		System.out.println(userName.toString());
	}
	/*
	 *  测试请假类型
	 * */
	@Test
	public void testGeQjlx() {
	    List<Qjlx> qjlx = qjlxrepository.getQjlx();
		System.out.println(qjlx);
	}
}






