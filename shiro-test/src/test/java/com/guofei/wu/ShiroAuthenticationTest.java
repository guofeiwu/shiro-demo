package com.guofei.wu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 吴国飞 (guofei.wu)
 * @author 吴国飞 (guofei.wu)
 * @version 2018/10/12
 * @since 2018/10/12
 */
public class ShiroAuthenticationTest {

    SimpleAccountRealm accountRealm;

    @Before
    public void realm() {
        accountRealm = new SimpleAccountRealm();
        accountRealm.setName("jack");
        accountRealm.addAccount("tom", "123456");
    }


    @Test
    public void authenticationTest() {
        // 构建security manager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        // security manager 设置realm
        defaultSecurityManager.setRealm(accountRealm);

        // 设置security manager
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        // 构建认证主体
        Subject subject = SecurityUtils.getSubject();

        // 构建认证的token
        AuthenticationToken token = new UsernamePasswordToken("tom", "123456");

        subject.login(token);


        subject.hasRole("");

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

    }

}
