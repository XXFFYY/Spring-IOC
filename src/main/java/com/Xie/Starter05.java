package com.Xie;

import com.Xie.controller.TypeController;
import com.Xie.service.RoleService;
import com.Xie.utils.PropertyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.relation.Role;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 10:57
 */
public class Starter05 {
    public static void main(String[] args) {
        //获取spring的上下文环境
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring05.xml");

        RoleService roleService = (RoleService) beanFactory.getBean("roleService");

        RoleService roleService02 = (RoleService) beanFactory.getBean("roleService");
    }
}
