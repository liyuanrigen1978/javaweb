package com.liyuan.myssm.myspringmvc;

import com.liyuan.myssm.io.BeanFactory;
import com.liyuan.myssm.io.ClassPathXmlApplicationContext;
import com.liyuan.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author liyuan_start
 * @create 2022-06-02 22:52
 * 添加中央控制器来接受所有的请求
 */
//表示拦截所有以.do为结尾的请求
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    //解析xml配置文件
    public DispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();

    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //编码
        request.setCharacterEncoding("UTF-8");
        //从URL获取servletPath /user.do
        String servletPath = request.getServletPath();
        //然后从得到的servletPath/user.do中截取user字符串
        servletPath = servletPath.substring(1); //从第一位截取，/user.do变成user.do
        int lastDotIndex = servletPath.lastIndexOf(".do");//找到从.do开始的索引
        servletPath = servletPath.substring(0, lastDotIndex); //将user字符串取出

        //从Map中取出数据，对应到xml文件中的UserController上去
        Object controllerBeanObj = beanFactory.getBean(servletPath);


        //**************************从UserControllers中截取的部分 STR********************************
        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            //获取此类中的方法
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (operate.equals(method.getName())) {
                    Parameter[] parameters = method.getParameters();
                    //1-2.parameterValues 用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        //如果参数名是request,response,session 那么就不是通过请求中获取参数的方式了
                        if ("request".equals(parameterName)) {
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = request.getSession();
                        } else {
                            //从请求中获取参数值
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;

                            if (parameterObj != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }

                            parameterValues[i] = parameterObj;
                        }
                    }

                    //2.controller组件中的方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);
                    //3.视图处理
                    String methodReturnStr = (String) returnObj;

                    if (methodReturnStr.startsWith("redirect:")) {        //比如：  redirect:fruit.do
                        //从"redirect:"开始截取，"redirect:user.do" → "user.do"
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        //然后进行从定向处理
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, request, response);    // 比如：  "edit"
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //**************************从UserControllers中截取的部分 END********************************


    }
}
