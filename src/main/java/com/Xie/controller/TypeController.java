package com.Xie.controller;

import com.Xie.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Description: XXX
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/7/24 10:41
 */

@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;


    public void test(){
        System.out.println("TypeController...");
        typeService.test();

    }
}
