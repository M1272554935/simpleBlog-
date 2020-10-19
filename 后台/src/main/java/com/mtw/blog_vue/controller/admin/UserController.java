package com.mtw.blog_vue.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.pojo.UserDomain;
import com.mtw.blog_vue.service.UserService;
import com.mtw.blog_vue.utils.ImageVerificationCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;



    //登录
    @PostMapping("/toLogin")
    public String doLogin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "verificationCode") String verCapitalsCode,
            HttpServletRequest request, HttpServletResponse response
    ){
        if (!verCodeCompare(request, verCapitalsCode)){
            return "验证码错误！";
        }
        UserDomain testUser = userService.getUserByName(username);
        if (testUser == null){
            return "该账号不存在";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,true);
        try {
            subject.login(token);
            //将昵称存入session
            request.getSession().setAttribute("screenName",testUser.getScreenName());
            return "200";
        } catch (UnknownAccountException e) {
            LOGGER.error(e.getMessage());

            return "账号不存在";
        } catch (IncorrectCredentialsException e) {
            LOGGER.error(e.getMessage());

            return "密码错误";
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage());

            return "登录失败";
        }
    }

    //注销登录
    @GetMapping("/logout")
    public int logout(){
        Subject loutSubject=SecurityUtils.getSubject();
        loutSubject.logout();
        return 200;
    }

    /**
     * 注册
     * @param screenname
     * @param email
     * @param username
     * @param password
     * @param verCapitalsCode
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/toRegister")
    public String doRegister(
            @RequestParam(value = "screenname") String screenname,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "verificationCode") String verCapitalsCode,
            HttpServletRequest request, HttpServletResponse response
    ){
        if (!verCodeCompare(request, verCapitalsCode)){
            return "验证码错误！";
        }
        UserDomain userByName = userService.getUserByName(username);
        if (userByName != null){
            return "用户已存在！";
        }
        UserDomain userDomain = new UserDomain();
        userDomain.setUsername(username);
        userDomain.setScreenName(screenname);
        userDomain.setPassword(password);
        userDomain.setEmail(email);
        boolean b = userService.addUser(userDomain);
        if (b){
            return "200";
        }
        return "注册失败";
    }

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    @RequiresRoles(value = "admin")
    @GetMapping("/admin/getUserList")
    public Page<UserDomain> getUserList(
            @RequestParam(name = "page"  ,defaultValue = "1" ,required = false) int page,
            @RequestParam(name = "pageSize"  ,defaultValue = "15",required = false) int pageSize
    ){
        Page<UserDomain> all = userService.getAll(page, pageSize);

        return all;
    }

    /**
     * 设置用户角色
     * @param uid
     * @param role
     * @return
     */
    @RequiresRoles(value = "admin")
    @GetMapping("/admin/changeRole")
    public String changeRole(
            @RequestParam(name = "uid"  ) int uid,
            @RequestParam(name = "role") String role
    ){
        UserDomain userRole = new UserDomain();
        userRole.setRole(role);
        userRole.setUid(uid);
        try{
            userService.updateUser(userRole);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return "出现错误了";
        }
        return  "200";
    }

    /**
     * 删除用户
     * @param uid
     * @return
     */
    @RequiresRoles(value = "admin")
    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam(name = "uid"  ) int uid){

        if (uid <=0){
            return "错误";
        }
        try {
            userService.deleteUser(uid);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return "服务器错误";
        }
        return "200";
    }

    /**
     * 验证码生成
     * @param response
     * @throws IOException
     */
    @GetMapping("getCode")
    public void getVerifiCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageVerificationCode ivc = new ImageVerificationCode();
        BufferedImage image = ivc.getImage();
        request.getSession().setAttribute("yzCode", ivc.getText());
        ivc.output(image, response.getOutputStream());
    }

    /**
     * 验证码验证
     * @param request
     * @param verCapitalsCode
     * @return
     */
    public boolean verCodeCompare(HttpServletRequest request,String verCapitalsCode){
        String capitalsCode = (String)request.getSession().getAttribute("yzCode");
        //转小写
        String yzCode = capitalsCode.toLowerCase();
        String verCode = verCapitalsCode.toLowerCase();

        return yzCode.equals(verCode);
    }
}
