package com.atguigu.user.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.user.dao.UserDao;
import com.atguigu.user.dao.impl.UserDaoImpl;
import com.atguigu.user.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liyuan_start
 * @create 2022-06-01 18:26
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    private UserDao userDao = new UserDaoImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //获取参数
        //id
        String idStr = request.getParameter("id");
        Integer id = Integer.parseInt(idStr);
        //用户名
        String username = request.getParameter("username");
        //密码
        String password = request.getParameter("password");

        userDao.updateUser(new User(id,username,password));
        //页面不是调到.html页面。而是要从定向跳向IndexServlet重新刷新首页
        response.sendRedirect("index");


    }
}
