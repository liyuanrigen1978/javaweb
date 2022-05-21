package com.atguigu.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liyuan_start
 * @create 2022-05-21 14:58
 */


public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //名称 这个是字符串，数值型的要进行数值转换
        String fnameStr = req.getParameter("fname");
        //价格
        String priceStr = req.getParameter("price");
        Integer price = Integer.parseInt(priceStr);
        //库存
        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        //备注
        String remarkStr = req.getParameter("remark");


        System.out.println(fnameStr + price + fcount + remarkStr);

    }
}
