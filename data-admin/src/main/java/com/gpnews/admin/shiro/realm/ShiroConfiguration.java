package com.gpnews.admin.shiro.realm;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
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
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecuritManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/logout", "logout");

        //过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
        filters.put("authc", new CustomFormAuthenticationFilter());//将自定义 的FormAuthenticationFilter注入shiroFilter中

        filterChainDefinitionMap.put("/login", "anon"); 	   //不拦截登录请求
        filterChainDefinitionMap.put("/script/**", "anon");    //不拦截js
        filterChainDefinitionMap.put("/css/**", "anon");       //不拦截css
        filterChainDefinitionMap.put("/js/**", "anon");        //不拦截js
        filterChainDefinitionMap.put("/img/**", "anon");       //不拦截图片
        filterChainDefinitionMap.put("/img2/**", "anon");      //不拦截图片
        filterChainDefinitionMap.put("/public/**", "anon");    //不拦截公共资源
        //filterChainDefinitionMap.put("/index", "roles[ADMIN]"); //设置特定角色访问
//        filterChainDefinitionMap.put("/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");



        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

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
