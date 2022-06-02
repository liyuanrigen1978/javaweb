package com.atguigu.user.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;
import com.atguigu.user.dao.UserDao;
import com.atguigu.user.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liyuan_start
 * @create 2022-06-01 21:48
 */
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

            if(StringUtil.isNotEmpty(idStr)){
                int id = Integer.parseInt(idStr);
                userDao.delUser(id);
                resp.sendRedirect("index");
            }

    }
}
