package com.nixum.webAdmin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestApiController {

    @GetMapping("/test")
    public String testApi(String username) {
        System.out.println("testApi" + username);
        return "yesyyseysyey";
    }
}
