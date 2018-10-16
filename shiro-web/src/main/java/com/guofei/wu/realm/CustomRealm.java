package com.guofei.wu.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 吴国飞 (guofei.wu)
 * @author 吴国飞 (guofei.wu)
 * @version 2018/10/12
 * @since 2018/10/12
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> map = new HashMap(16);

    {
        map.put("tom", "e10adc3949ba59abbe56e057f20f883e");
        map.put("jack", "e10adc3949ba59abbe56e057f20f883e");
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();

        Set<String> roles = getRolesByUserName(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);

        Set<String> permissions = getPermissionsByUserName(username);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 模拟数据库操作
     *
     * @param username
     * @return java.lang.String
     * @since 2018/10/12
     */
    private Set<String> getPermissionsByUserName(String username) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("user:delete");
        permissions.add("user:select");
        return permissions;
    }

    /**
     * 模拟数据库操作
     *
     * @param username
     * @return java.lang.String
     * @since 2018/10/12
     */
    private Set<String> getRolesByUserName(String username) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();

        // 数据库中的密码
        String password = getPasswordByUserName(username);
        if (password == null) {
            return null;
        }
        // 传入的username 等于doGetAuthorizationInfo方法的参数 principals
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, "customRealm");
        return info;
    }

    /**
     * 模拟数据库操作
     *
     * @param username
     * @return java.lang.String
     * @since 2018/10/12
     */
    private String getPasswordByUserName(String username) {
        return map.get(username);
    }
}
