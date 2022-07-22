package com.Xie;

import com.Xie.service.AccountService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 10:57
 */
public class Starter02 {
    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("spring02.xml");

        //使用空构造实例化
        AccountService accountService = (AccountService) factory.getBean("accountService");

        accountService.test();
    }
}
