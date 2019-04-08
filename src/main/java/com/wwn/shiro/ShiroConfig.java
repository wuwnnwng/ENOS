package com.wwn.shiro;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
 
@Configuration
public class ShiroConfig {
	
	 
	@Bean
	public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/bootstrap/**", "anon");
		filterChainDefinitionMap.put("/easyui/**", "anon");
		filterChainDefinitionMap.put("/style/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/wjmm", "anon");//忘记密码
		filterChainDefinitionMap.put("/register", "anon");//注册
		filterChainDefinitionMap.put("/userInfo/userList", "authc,roles[admin],perms[userInfo:view]");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/loginPage");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//		shiroFilterFactoryBean.setd
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
	    filterChainDefinitionMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
    
	
	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
//		myShiroRealm.setCachingEnabled(true);
		return myShiroRealm;
	}


	@Bean
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		
		securityManager.setRealm(myShiroRealm());
	    securityManager.setRememberMeManager(rememberMeManager()); //配置记住我  
//		securityManager.setCacheManager(ehCacheManager());//配置缓存  
		
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");//设置编码方式
		matcher.setHashIterations(1);//设置迭代次数
		myShiroRealm().setCredentialsMatcher(matcher);
		
		return securityManager;
	}
	
	@Bean
	public SimpleCookie rememberMeCookie(){
	    //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
	    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
	    //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
	    //setcookie()的第七个参数
	    //设为true后，只能通过http访问，javascript无法访问
	    //防止xss读取cookie
	    simpleCookie.setHttpOnly(true);
//	    simpleCookie.setPath("");
	    //<!-- 记住我cookie生效时间30天 ,单位秒;-->
	    simpleCookie.setMaxAge(2592000);
	    return simpleCookie;
	}
	/**
	 * cookie管理对象;记住我功能,rememberMe管理器
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    cookieRememberMeManager.setCookie(rememberMeCookie());
	    //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
	    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	    return cookieRememberMeManager;
	}
	
	/*
	 * 
	 * 页面出错的处理
	 * */
//    @Bean(name="simpleMappingExceptionResolver")
//    public SimpleMappingExceptionResolver
//    createSimpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//        Properties mappings = new Properties();
//        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//        mappings.setProperty("UnauthorizedException","403");
//        r.setExceptionMappings(mappings);  // None by default
//        r.setDefaultErrorView("error");    // No default
//        r.setExceptionAttribute("ex");     // Default is "exception"
//        //r.setWarnLogCategory("example.MvcLogger");     // No default
//        return r;
//    }
    /*
     * 页面使用shiro标签 需要配这个bean 
     * */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
//	@Bean
//    public EhCacheManager ehCacheManager() {  
//		EhCacheManager ehCacheManager = new EhCacheManager();  
//		ehCacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");  
//        return ehCacheManager;  
//    }  
	  
	/**
	 * FormAuthenticationFilter 过滤器 过滤记住我
	 * @return
	 */
//	@Bean
//	public FormAuthenticationFilter formAuthenticationFilter(){
//	    FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//	    //对应前端的checkbox的name = rememberMe
//	    formAuthenticationFilter.setRememberMeParam("rememberMe");
//	    return formAuthenticationFilter;
//	}
	 
 
}
