package com.cy.pj.common.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration  //配置类
public class SpringShiroConfig {

    @Bean   //被注解的方法，返回值会交给spring管理，相当于我们写业务时加的注解
            //参数中的Realm方法由spring框架自动注入依赖，相当于加了@Autowired
    public SecurityManager securityManager(
            Realm realm,
            CacheManager shiroCacheManager,
            RememberMeManager rememberMeManager,
            SessionManager sessionManager){
        DefaultWebSecurityManager securityManager=
                new DefaultWebSecurityManager();
        //将Realm对象传给SecurityManager，SecurityManager（安全管理器）会调用realm进行安全验证
        securityManager.setRealm(realm);
        //
        securityManager.setCacheManager(shiroCacheManager);
        //注入 记住我 对象
        securityManager.setRememberMeManager(rememberMeManager);
        //注入 会话管理对象
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean   //定义aop切面规则
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            SecurityManager securityManager){

        ShiroFilterFactoryBean factoryBean =
                new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);

        factoryBean.setLoginUrl("/doLoginUI");
        LinkedHashMap<String,String> filterMap= new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        filterMap.put("/bower_components/**","anon");
        filterMap.put("/build/**","anon");
        filterMap.put("/dist/**","anon");
        filterMap.put("/plugins/**","anon");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        filterMap.put("/user/doLogin", "anon");
        filterMap.put("/doLogout","logout");


        filterMap.put("/**","user");//authc
        factoryBean.setFilterChainDefinitionMap(filterMap);

        return factoryBean;
    }

    /**授权属性资源管理员（spring原生aop的advisor顾问）*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager
    ){
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**授权缓存管理对象*/
    @Bean
    public CacheManager shiroCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cManager=
                new CookieRememberMeManager();
        SimpleCookie cookie=new SimpleCookie("rememberMe");
        cookie.setMaxAge(7*24*60*60);
        cManager.setCookie(cookie);
        return cManager;
    }

    /**授权会话管理对象*/
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sManager=
                new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60*60*1000);//设置会话超时时间为一小时
        return sManager;
    }



}
