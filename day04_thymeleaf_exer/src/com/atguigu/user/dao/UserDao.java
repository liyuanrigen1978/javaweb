package com.atguigu.user.dao;

import com.atguigu.user.pojo.User;

import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-05-22 1:49
 */
public interface UserDao {


    List<User> getUserList();
}
