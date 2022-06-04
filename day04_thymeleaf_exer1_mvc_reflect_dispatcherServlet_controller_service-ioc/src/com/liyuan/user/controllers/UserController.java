package com.liyuan.user.controllers;

import com.liyuan.myssm.util.StringUtil;
import com.liyuan.user.pojo.User;
import com.liyuan.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-06-02 21:11
 */

//@WebServlet("/user.do")
public class UserController{

    private UserService userService = null;

    //首页
    private String index(String oper,String keyword,Integer pageNo,HttpServletRequest request) {

        //2 设定session作用域
        HttpSession session = request.getSession();

        if(pageNo==null){
            pageNo=1;
        }


       //*******************查询表单和分页处理 STR***************************
        //查询处理
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            pageNo = 1;
           //如果keyword是空的话，要设定为""字符串
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            //将查询框中的输入keyword值存入(覆盖)到session中
            session.setAttribute("keyword", keyword);
        } else {
           //当进行上一页，下一页操作是,获取存储在session中的keyword的值来进行操作
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }
        //*******************查询表单和分页处理 END***************************

        //所有用户消息
        List<User> userList = userService.getUserList(keyword, pageNo);
        //取得总页数
        int pageCount = userService.getPageCount(keyword);

        //页面数保存到session
        session.setAttribute("pageNo", pageNo);
        //将所有取得的用户消息保存到session
        session.setAttribute("userList", userList);
        //将总页数保存到session
        session.setAttribute("pageCount", pageCount);
        return "index";

    }

    //追加新数据
    private String add(String username,String password){
        userService.addUser(new User(0, username, password));
        return "redirect:user.do";
    }

    //删除数据
    private String del(Integer id) {
        if (id!=null) {
            userService.delUser(id);
            return "redirect:user.do";
        }
        return "error";
    }

    //显示指定数据
    private String edit(Integer id,HttpServletRequest request) {
        if (id!=null) {
            //调用dao中的方法，获得user对象
            User user = userService.getUserById(id);
            //把取得的user对象加到这次请求中，用来网页表示
            request.setAttribute("user", user);
            //运用上下文（实际的配置文件在web.xml里），跳转到/edit.html页面
            //super.processTemplate("edit", request, response);
            return "edit";


        }
        return "error";
    }

    //更新数据
    private String update(Integer id,String username,String password) {

        userService.updateUser(new User(id, username, password));
        //页面不是调到.html页面。而是要从定向跳向IndexServlet重新刷新首页
        //页面跳转处理不放在Controller里进行，将统一进行处理
        //response.sendRedirect("user.do");
        return "redirect:user.do";
    }
}
