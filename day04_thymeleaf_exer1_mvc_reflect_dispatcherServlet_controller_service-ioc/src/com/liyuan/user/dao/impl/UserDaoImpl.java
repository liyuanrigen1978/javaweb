package com.liyuan.user.dao.impl;

import com.liyuan.myssm.basedao.BaseDAO;
import com.liyuan.user.dao.UserDao;
import com.liyuan.user.pojo.User;

import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-05-22 1:51
 */
public class UserDaoImpl extends BaseDAO<User> implements UserDao {
    @Override
//    public List<User> getUserList(Integer pageNo) {
//
//        return super.executeQuery("select * from user_tbl limit ?,5",(pageNo-1)* 5);
//    }
    public List<User> getUserList(String keyword, Integer pageNo) {

        return super.executeQuery("select * from user_tbl where username like ?  limit ?,5","%"+keyword+"%",(pageNo-1)* 5);
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

    @Override
//    public int getUserCount() {
//        //返回的数值类型是Long型，要进行转换，不然会出错
//        Long countLong = (Long) super.executeComplexQuery("select count(*) from user_tbl")[0];
//        int userCount = countLong.intValue();
//        return userCount;
//    }

    public int getUserCount(String keyword) {
        //返回的数值类型是Long型，要进行转换，不然会出错
        Long countLong = (Long) super.executeComplexQuery("select count(*) from user_tbl where username like ?","%"+keyword+"%")[0];
        int userCount = countLong.intValue();
        return userCount;
    }

}
