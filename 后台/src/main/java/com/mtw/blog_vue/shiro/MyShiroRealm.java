package com.mtw.blog_vue.shiro;


import com.mtw.blog_vue.pojo.UserDomain;
import com.mtw.blog_vue.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

        private static final Logger LOGGER = LogManager.getLogger(MyShiroRealm.class);

        @Autowired
        private UserService userService;

        /**
         *  授权
         */
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //获取登录用户名及角色
            UserDomain currentUser = (UserDomain)SecurityUtils.getSubject().getPrincipal();
            info.addRole(currentUser.getRole());

            return info;
        }


        /**
         * 认证
         */
        @Override
        public SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
                throws AuthenticationException {


            UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
            UserDomain userByUname = userService.getUserByName(usertoken.getUsername());

            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    userByUname,                                                    //使用user对象
                    userByUname.getPassword(),                                     //密码
                    ByteSource.Util.bytes(userByUname.getUsername()),getName());  //realm name

                //密码认证
            return simpleAuthenticationInfo;

        }
}

