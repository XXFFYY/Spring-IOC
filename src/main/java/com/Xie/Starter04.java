package com.Xie;

import com.Xie.controller.TypeController;
import com.Xie.dao.TypeDao;
import com.Xie.service.AccountService;
import com.Xie.service.TypeService;
import com.Xie.utils.PropertyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 10:57
 */
public class Starter04 {
    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring04.xml");
        /*TypeDao typeDao = (TypeDao) ac.getBean("typeDao");

        TypeService typeService = (TypeService) ac.getBean("typeService");
        typeService.test();*/
        //typeDao.test();
        TypeController typeController = (TypeController) ac.getBean("typeController");
        typeController.test();

        PropertyUtils propertyUtils = (PropertyUtils) ac.getBean("propertyUtils");
        propertyUtils.test();
    }
}
