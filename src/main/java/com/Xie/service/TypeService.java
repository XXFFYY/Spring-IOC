package com.Xie.service;

import com.Xie.dao.TypeDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: set方法注入
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 15:55
 */
@Service
public class TypeService {
    //bean对象
    @Resource
    private TypeDao typeDao;
    /*
        set方法注入
            需要给属性字段提供set方法
     */

   /* public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }
    //字符串类型
    private String host;
    //set方法
    public void setHost(String host) {
        this.host = host;
    }
    //整型
    private int port;
    //set方法
    public void setPort(int port) {
        this.port = port;
    }*/



    /**
     * 构造器注入
     *  通过构造器的形参设置属性字段的值
     */

    public void test(){
        System.out.println("TypeService...");
        typeDao.test();
       // System.out.println("Host:" + host + ",Port:" + port);
    }
}
