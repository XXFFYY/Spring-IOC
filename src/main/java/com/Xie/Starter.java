package com.Xie;

import com.Xie.dao.UserDao;
import com.Xie.service.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Description: spring配置文件加载
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 10:00
 */
public class Starter {
    public static void main(String[] args) {
        /*
            1.通过相对路径加载配置文件
                ClassPathXmlApplicationContext(相对路径)
        */
        //BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");

        /*
            2.通过绝对路径加载配置文件
                FileSystemXmlApplicationContext(绝对路径)
         */

        //BeanFactory factory = new FileSystemXmlApplicationContext("C:\\JavaTest\\ideaWorkSpace\\spring01\\src\\main\\resources\\spring.xml");

        /*
            3.多个配置文件加载
         */

        // **通过可变参数，设置多个配置文件
        //                    ClassPathXmlApplicationContext(配置文件1,配置文件2...)
        //BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml", "beans.xml");

        // **设置一个总配置文件，在总配置文件中导入需要加载的配置文件
        BeanFactory factory = new ClassPathXmlApplicationContext("service.xml");
        UserService userService = (UserService) factory.getBean("userService");
        userService.test();

        UserDao userDao = (UserDao) factory.getBean("userDao");
        userDao.test();


    }
}
