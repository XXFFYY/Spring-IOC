package com.Xie.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/26 17:21
 */
public class RoleService implements InitializingBean {

    public void test(){
        System.out.println("RoleService...");
    }

    /*
        查看初始化的方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("RoleService init afterPropertiesSet...");
    }

/*    public RoleService() {
        System.out.println("RoleService被初始化了");
    }*/

    /**
     * 配置文件中指定的查看初始化的方法
     */
    /*public void init(){
        System.out.println("RoleService被初始化了...");
    }*/
    public void destroy(){
        System.out.println("RoleService销毁了...");
    }
}
