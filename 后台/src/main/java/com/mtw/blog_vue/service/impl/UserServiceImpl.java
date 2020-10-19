package com.mtw.blog_vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtw.blog_vue.mapper.UserMapper;
import com.mtw.blog_vue.pojo.UserDomain;
import com.mtw.blog_vue.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名查询
     * @param uName
     * @return
     */
    @Override
    public UserDomain getUserByName(String uName) {
        if (uName != null){
            QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
            wrapper.eq("username",uName);

            return userMapper.selectOne(wrapper);
        }
        return null;
    }

    /**
     * 根据用户昵称查询
     * @param ScreenName
     * @return
     */
    @Override
    public UserDomain getByScreenName(String ScreenName) {
        if (ScreenName != null){
            QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
            wrapper.eq("screenName",ScreenName);

            return userMapper.selectOne(wrapper);
        }
        return null;
    }

    @Override
    public boolean addUser(UserDomain userDomain) {
        if (userDomain != null){
            UserDomain userByName = getUserByName(userDomain.getUsername());
            if (userByName == null){
                userMapper.insert(userDomain);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(Integer uid) {
        if (uid > 0 ){
            UserDomain userById = getUserById(uid);
            if (userById != null){
                userMapper.deleteById(uid);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDomain getUserById(Integer id) {
        if ( id >0){
            return userMapper.selectById(id);
        }
        return null;
    }

    @Override
    public boolean updateUser(UserDomain userDomain) {

        if (userDomain != null){
            try{
                userMapper.updateById(userDomain);
                return true;
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }

        }
        return false;
    }

    @Override
    public Page<UserDomain> getAll(int currentPage, int pageSize){

        Page<UserDomain> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserDomain> wrapper = new QueryWrapper<>();
        wrapper.select("uid","username","email","role","screenName","createTime");
        wrapper.isNotNull("uid");
        wrapper.eq("role","v1").or().eq("role","v8");

        Page<UserDomain> userDomainPage = userMapper.selectPage(page, wrapper);

        return userDomainPage;

    }
}
