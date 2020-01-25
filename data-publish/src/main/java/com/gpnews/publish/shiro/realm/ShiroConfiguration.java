package com.gpnews.publish.shiro.realm;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private String timeout;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
//        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public FormAuthenticationFilter authorizationFilter() {
        return new CustomAuthorizationFilter();
    }
    @Bean
    public AnonymousFilter anonymousFilter(){
        return new AnonymousFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecuritManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", authorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    //配置redisManager
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setExpire(3600);//配置缓存过期时间(秒)
        return redisManager;
    }

    //session管理
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager ();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        sessionManager.setSessionListeners(listeners);
        //设置session超时时间为1小时(单位毫秒)
        sessionManager.setGlobalSessionTimeout(-1);//永不超时
        //设置redisSessionDao
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(myShiroRealm());
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager());
        //配置自定义缓存redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    // 让shiro注解生效
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager); // 这里需要注入 SecurityManger 安全管理器
        return authorizationAttributeSourceAdvisor;
    }

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //指定加密方式
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        System.out.println("加密方式：MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(1024);
//        //此处的设置，true加密用的hex编码，false用的base64编码
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }

    //配置cacheManager
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


}
