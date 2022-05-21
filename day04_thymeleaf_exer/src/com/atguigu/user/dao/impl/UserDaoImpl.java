package com.atguigu.user.dao.impl;

import com.atguigu.myssm.basedao.BaseDAO;
import com.atguigu.user.dao.UserDao;
import com.atguigu.user.pojo.User;

import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-05-22 1:51
 */
public class UserDaoImpl extends BaseDAO<User> implements UserDao {
    @Override
    public List<User> getUserList() {
        return super.executeQuery("select * from user_tbl");
    }

}
