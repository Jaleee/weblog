package com.jale.weblog.user.service.shiro;

import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     * @param collection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        System.out.println("执行授权逻辑");

        // 给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加资源的授权字符串
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        User dbUser = userService.findByUserName(userName);
        info.addRole(dbUser.getRole());
        info.addStringPermission(dbUser.getPerms());

        return info;
    }

    /**
     * 执行认证逻辑
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        String userName = (String) token.getPrincipal();

        User user = userService.findByUserName(userName);

        if(user == null) {
           return null;
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    }
}
