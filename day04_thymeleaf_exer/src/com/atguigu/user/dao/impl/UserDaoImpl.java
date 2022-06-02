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

    @Override
    public User getUserByFid(String username, String password) {
        return super.load("select * from user_tbl where username = ? and password = ? ", username, password);
    }

    @Override
    public User getUserById(Integer id) {
        return super.load("select * from user_tbl where id = ? ", id);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user_tbl set username = ? , password = ? where id = ? " ;
        super.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getId());
    }

    @Override
    public void delUser(Integer id) {
        String sql = "delete from user_tbl where id = ?";
        super.executeUpdate(sql,id);
    }

    @Override
    public void addUser(User user) {
        super.executeUpdate("insert into user_tbl value(0,?,?)",user.getUsername(),user.getPassword());
    }

}
