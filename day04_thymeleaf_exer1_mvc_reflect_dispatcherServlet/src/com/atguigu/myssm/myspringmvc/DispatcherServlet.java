package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyuan_start
 * @create 2022-06-02 22:52
 * 添加中央控制器来接受所有的请求
 */
//表示拦截所有以.do为结尾的请求
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    private Map<String,Object> beanMap = new HashMap<>();

    //解析xml配置文件
    public DispatcherServlet(){}

    @Override
    public void init(){
        try {
            //读取xml的文件信息
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(inputStream);

            //4.获取所有的bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i = 0 ; i<beanNodeList.getLength() ; i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode ;
                    String beanId =  beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance() ;
                    Method setServletContextMethod = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
                    setServletContextMethod.invoke(beanObj,this.getServletContext());
//                    Field servletContextField = controllerBeanClass.getDeclaredField("servletContext");
//                    servletContextField.setAccessible(true);
//                    servletContextField.set(beanObj,this.getServletContext());
//                    Method setServletContextMethod = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
//                    setServletContextMethod.invoke(beanObj , this.getServletContext());

                    beanMap.put(beanId , beanObj) ;
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }





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

        //从Map中取出数据，对应到xml文件中的UserController上去
        Object userControllerObj = beanMap.get(servletPath);


        //**************************从UserControllers中截取的部分 STR********************************
        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            //获取此类中的方法
            Method method = userControllerObj.getClass().getDeclaredMethod(operate,HttpServletRequest.class,HttpServletResponse.class);
            if(method!=null){
                method.setAccessible(true);
                method.invoke(userControllerObj,request,response);
            }else{
                throw new RuntimeException("operate值非法!");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //**************************从UserControllers中截取的部分 END********************************


    }
}
