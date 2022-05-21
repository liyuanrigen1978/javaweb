package com.atguigu.user.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.user.dao.UserDao;
import com.atguigu.user.dao.impl.UserDaoImpl;
import com.atguigu.user.pojo.User;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-05-22 1:48
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException, ServletException {
        UserDao userDao = new UserDaoImpl();
        List<User> userList = userDao.getUserList();
        //保存到session作用域
        HttpSession session = request.getSession() ;
        session.setAttribute("userList",userList);
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index",request,response);
    }
}
