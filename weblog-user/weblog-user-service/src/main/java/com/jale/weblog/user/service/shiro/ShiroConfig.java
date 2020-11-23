package com.jale.weblog.user.service.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 1.创建shiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加shiro内置过滤器，可以实现权限相关的拦截器
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/user/login", "anon");
        filterMap.put("/api/**", "anon");
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        // 设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/noAuth");

        return shiroFilterFactoryBean;
    }

    // 2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(realm);
        return securityManager;
    }

    // 3.创建自定义realm
    @Bean
    public UserRealm getRealm() {
        UserRealm userRealm = new UserRealm();
        // 修改凭证校验匹配器
        /*HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(credentialsMatcher);*/
        return userRealm;
    }

    /**
     * 配置shiroDialect，用于thymeleaf配合使用
     */
    /*@Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }*/

}
