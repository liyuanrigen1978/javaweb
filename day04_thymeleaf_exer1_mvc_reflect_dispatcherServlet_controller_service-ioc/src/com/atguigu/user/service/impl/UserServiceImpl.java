package com.atguigu.user.service.impl;

import com.atguigu.user.dao.UserDao;
import com.atguigu.user.dao.impl.UserDaoImpl;
import com.atguigu.user.pojo.User;
import com.atguigu.user.service.UserService;

import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-06-05 0:04
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public List<User> getUserList(String keyword, Integer pageNo) {
        return userDao.getUserList(keyword,pageNo);
    }

//    @Override
//    public User getUserByFid(String username, String password) {
//        return userDao.getUserById(username,password);
//    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void delUser(Integer id) {
        userDao.delUser(id);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public Integer getPageCount(String keyword) {
        int count  = userDao.getUserCount(keyword);
        int pageCount = (count+5-1)/5;
        return pageCount;
    }
}
