package com.atguigu.myssm.myspringmvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liyuan_start
 * @create 2022-06-02 22:52
 * 添加中央控制器来接受所有的请求
 */
//表示拦截所有以.do为结尾的请求
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 编码
        request.setCharacterEncoding("UTF-8");
        //从URL获取servletPath /user.do
        String servletPath = request.getServletPath();
        //然后从得到的servletPath/user.do中截取user字符串
        servletPath = servletPath.substring(1); //从第一位截取，/user.do变成user.do
        int lastDotIndex = servletPath.lastIndexOf(".do");//找到从.do开始的索引
        servletPath =servletPath.substring(0,lastDotIndex); //将user字符串取出


        //在对应到UserController上去

    }
}
