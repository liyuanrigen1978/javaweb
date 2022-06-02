package com.atguigu.user.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private UserDao userDao = new UserDaoImpl();
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException, ServletException {
        //1 编码
        request.setCharacterEncoding("UTF-8");
        //2 设定session作用域
        HttpSession session = request.getSession() ;
        //查询的关键字
        String keyword = null;
        //默认显示的页面是第一页
        Integer pageNo =1;


        //*******************查询表单和分页处理 STR***************************
        //查询处理
        //3 获取查询表单的"oper"属性和值
        String oper = request.getParameter("oper");
        //如果oper！=null 说明 这个操作是通过查询表单的查询按钮点击过来的
        //如果oper==null  说明 这个操作不是通过查询表单的操作点击过来的
        if(StringUtil.isNotEmpty(oper) && "search".equals(oper)){
            //说明 这个操作是通过查询表单的查询按钮点击过来的
            //所以，pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            //如果keyword是空的话，要设定为""字符串
            if(StringUtil.isEmpty(keyword)){
                keyword="";
            }
            //如果keyword不为空的话，将值存入session中
            session.setAttribute("keyword",keyword);
        }else{
            //这个操作不是通过查询表单的操作点击过来的,keyword应该从session中获取
            //分页处理
            String pageStr = request.getParameter("pageNo");
            if(StringUtil.isNotEmpty(pageStr)){
                pageNo = Integer.parseInt(pageStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj != null){
                keyword = (String)keywordObj;
            }else{
                keyword="";
            }
        }
        //*******************查询表单和分页处理 END***************************
        //所有用户消息
        List<User> userList = userDao.getUserList(keyword,pageNo);
        //取得所有的用户数
        int userCount = userDao.getUserCount(keyword);
        //总页数
        int pageCount = (userCount+5-1)/5;



        //页面数保存到session
        session.setAttribute("pageNo",pageNo);
        //将所有取得的用户消息保存到session
        session.setAttribute("userList",userList);
        //将总页数保存到session
        session.setAttribute("pageCount",pageCount);


        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index",request,response);
    }
}
