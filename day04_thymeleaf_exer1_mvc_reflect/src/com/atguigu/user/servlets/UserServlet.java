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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author liyuan_start
 * @create 2022-06-02 21:11
 */

@WebServlet("/user.do")
public class UserServlet extends ViewBaseServlet {
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 编码
        request.setCharacterEncoding("UTF-8");
        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        //获取此类中的所有方法
        Method[] methods = this.getClass().getDeclaredMethods();

        for (Method m : methods) {
            //获取方法名
            String methodName = m.getName();
            if (operate.equals(methodName)) {
                try {
                    //通过反射技术来调用对应得方法
                    m.invoke(this, request, response);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
        throw new RuntimeException("operate值非法！");

    }

    //首页
    private void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //2 设定session作用域
        HttpSession session = request.getSession();
        //查询的关键字
        String keyword = null;
        //默认显示的页面是第一页
        Integer pageNo = 1;


        //*******************查询表单和分页处理 STR***************************
        //查询处理
        //3 获取查询表单的"oper"属性和值
        String oper = request.getParameter("oper");
        //如果oper！=null 说明 这个操作是通过查询表单的查询按钮点击过来的
        //如果oper==null  说明 这个操作不是通过查询表单的操作点击过来的
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            //说明 这个操作是通过查询表单的查询按钮点击过来的
            //所以，pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            //如果keyword是空的话，要设定为""字符串
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            //将查询框中的输入keyword值存入(覆盖)到session中
            session.setAttribute("keyword", keyword);
        } else {
            //这个操作不是通过查询表单的操作点击过来的,keyword应该从session中获取
            //分页处理
            String pageStr = request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageStr)) {
                pageNo = Integer.parseInt(pageStr);
            }
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
        List<User> userList = userDao.getUserList(keyword, pageNo);
        //取得所有的用户数
        int userCount = userDao.getUserCount(keyword);
        //总页数
        int pageCount = (userCount + 5 - 1) / 5;


        //页面数保存到session
        session.setAttribute("pageNo", pageNo);
        //将所有取得的用户消息保存到session
        session.setAttribute("userList", userList);
        //将总页数保存到session
        session.setAttribute("pageCount", pageCount);


        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index", request, response);
    }

    //追加新数据
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        userDao.addUser(new User(0, username, password));
        resp.sendRedirect("user.do");

    }

    //删除数据
    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (StringUtil.isNotEmpty(idStr)) {
            int id = Integer.parseInt(idStr);
            userDao.delUser(id);
            resp.sendRedirect("user.do");
        }

    }

    //显示指定数据
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (StringUtil.isNotEmpty(idStr)) {
            //获取到的是字符串，所以要进行类型转换
            int id = Integer.parseInt(idStr);
            //调用dao中的方法，获得user对象
            User user = userDao.getUserById(id);
            //把取得的user对象加到这次请求中，用来网页表示
            request.setAttribute("user", user);
            //运用上下文（实际的配置文件在web.xml里），跳转到/edit.html页面
            super.processTemplate("edit", request, response);


        }
    }

    //更新数据
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        userDao.updateUser(new User(id, username, password));
        //页面不是调到.html页面。而是要从定向跳向IndexServlet重新刷新首页
        response.sendRedirect("user.do");
    }
}
