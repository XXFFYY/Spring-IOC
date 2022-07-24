package com.Xie.service;

import com.Xie.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/22 10:51
 */
public class AccountService {
    @Autowired
    @Qualifier(value = "ad")
    private AccountDao accountDao;
    public void test(){
        System.out.println("AccountService...");
        accountDao.test();
    }

    public AccountService() {
    }
    public AccountService(String name) {
    }
}
