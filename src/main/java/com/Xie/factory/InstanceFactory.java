package com.Xie.factory;

import com.Xie.service.AccountService;

/**
 * @Description: 实例化工厂
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 12:13
 */
public class InstanceFactory {

    //定义普通方法，返回实例化的bean对象
    public AccountService creatService(){
        return new AccountService();
    }
}
