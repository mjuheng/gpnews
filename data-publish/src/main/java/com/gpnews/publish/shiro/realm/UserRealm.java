package com.gpnews.publish.shiro.realm;

import com.gpnews.publish.service.PermissionService;
import com.gpnews.publish.service.RoleService;
import com.gpnews.publish.service.UserService;
import com.news.pojo.Permission;
import com.news.pojo.Role;
import com.news.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private PermissionService permissionServiceImpl;
    @Resource
    private RoleService roleServiceImpl;
    @Resource
    private UserService userServiceImpl;

    /*
     * Authorization 授权(权限操作验证,只有请求设置特定权限时调用此方法)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        User user = (User) principals.fromRealm(getName()).iterator().next();
        try {
            //根据用户id从数据库中查询用户角色
            Set<Role> userRoles = roleServiceImpl.queryByUserId(user.getId());
            for (Role role : userRoles) {
                roles.add(role.getName());
                //根据用户id从数据库中查询用户权限
                Set<Permission> userPermissions = permissionServiceImpl.queryByRoleId(role.getId());
                for (Permission permission: userPermissions){
                    permissions.add(permission.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加角色
        info.addRoles(roles);
        //添加权限
        info.addStringPermissions(permissions);
        return info;
    }

    /*
     * Authentication 验证(登录认证)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UserLoginToken userInfoToken = (UserLoginToken) authcToken;
        String username = userInfoToken.getUsername();
        SimpleAuthenticationInfo info = null;
        User userInfo = userServiceImpl.getMapper().selectOne(new User(username));
        if (userInfo != null){
            return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
        }
        return null;
    }



}
