package com.Xie.factory;

import com.Xie.service.AccountService;

/**
 * @Description: 定义静态工厂，返回实例化好的bean对象
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 11:27
 */
public class StaticFactory {
    public static AccountService creatService(){
        return new AccountService();
    }
}
