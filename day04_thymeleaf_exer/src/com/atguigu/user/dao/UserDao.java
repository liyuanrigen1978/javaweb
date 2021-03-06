package com.atguigu.user.dao;

import com.atguigu.user.pojo.User;

import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-05-22 1:49
 */
public interface UserDao {

    //获取所有用户列表
    List<User> getUserList();

    //根据用户名和密码获取用户对象
    User getUserByFid(String username,String password);

    //根据id获取指定用户的信息
    User getUserById(Integer id);

    //修改指定用户信息
    void updateUser(User user);

    //删除指定用户
    void delUser(Integer id);

    //增加用户
    void addUser(User user);
}
