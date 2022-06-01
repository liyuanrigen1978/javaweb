package com.atguigu.user.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;
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
 * @create 2022-06-01 1:48
 */

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    //声明一个dao对象，来和数据库进行交互
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if(StringUtil.isNotEmpty(idStr)){
            //获取到的是字符串，所以要进行类型转换
            int id = Integer.parseInt(idStr);
            //调用dao中的方法，获得user对象
            User user = userDao.getUserById(id);
            //把取得的user对象加到这次请求中，用来网页表示
            request.setAttribute("user",user);
            //运用上下文（实际的配置文件在web.xml里），跳转到/edit.html页面
            super.processTemplate("edit",request,response);


        }
    }
}
