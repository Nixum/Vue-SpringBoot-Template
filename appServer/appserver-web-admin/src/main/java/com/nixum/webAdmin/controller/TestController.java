package com.nixum.webAdmin.controller;

import com.nixum.model.dao.auth.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    UserMapper userDao;

    @RequestMapping("/testdao")
    @ResponseBody
    public String testDao() {
        System.out.println("test");
        System.out.println(userDao.findAllUserWithRole());
        return "123123";
    }

}
