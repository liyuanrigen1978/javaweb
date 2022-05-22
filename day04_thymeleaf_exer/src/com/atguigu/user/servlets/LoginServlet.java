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
 * @create 2022-05-22 20:21
 */
@WebServlet("/login.do")
public class LoginServlet extends ViewBaseServlet {
    private UserDao userDao = new UserDaoImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //2.获取参数

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDao.getUserByFid(username,password);

        if (user != null){
            req.setAttribute("user",user);
            resp.sendRedirect("index");
        }else{
            req.getRequestDispatcher("login.html").forward(req,resp);
        }

    }
}

