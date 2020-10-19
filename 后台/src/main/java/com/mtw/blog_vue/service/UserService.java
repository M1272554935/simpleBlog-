package com.mtw.blog_vue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.pojo.UserDomain;

public interface UserService {

    boolean addUser(UserDomain userDomain);

    boolean deleteUser(Integer uid);

    UserDomain getUserById(Integer id);

    boolean updateUser(UserDomain userDomain);

    UserDomain getUserByName(String uName);

    UserDomain getByScreenName(String ScreenName);

    Page<UserDomain> getAll(int currentPage, int pageSize);
}
