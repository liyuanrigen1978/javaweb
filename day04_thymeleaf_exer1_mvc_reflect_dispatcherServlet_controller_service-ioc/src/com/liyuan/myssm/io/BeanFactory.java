package com.liyuan.myssm.io;

/**
 * @author liyuan_start
 * @create 2022-06-05 0:43
 */

public interface BeanFactory {
    //定义一个方法，从xml文件中根据beanID，来获取组件
    Object getBean(String id);
}
