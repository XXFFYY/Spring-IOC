package com.Xie.service;

import com.Xie.dao.IUserDao;
import com.Xie.dao.UserDao;
import com.Xie.dao.UserDaoImpl01;

import javax.annotation.Resource;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 9:58
 */

/**
 * - 注解默认通过属性字段名称查找对应的bean对象（属性字段名称与bean标签id属性值一致）
 * - 如果属性字段不一样，则会通过类型（Class）查找
 * - 属性字段可以提供set方法，也可以不提供
 * - 注解可以声明在属性字段上 或set方法级别
 * - 可以设置注解的name属性，name属性值要与bean标签的id属性值一致（如果设置了name属性，则需要使用name属性查找bean对象）
 * - 当注入接口时，如果接口只有一个实现类，则正常实例化，如果接口有多个实现类，则需要使用name设置属性指定需要被实例化的bean对象
 */
public class UserService {

    //注入JavaBean对象
    @Resource
    private UserDao userDao;

    //set方法

/*    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }*/

    //注入接口
   @Resource(name = "userDaoImpl01")
    private IUserDao iUserDao ;


    public void test(){
        System.out.println("UserService...");
        userDao.test();
        iUserDao.test();
    }
}
