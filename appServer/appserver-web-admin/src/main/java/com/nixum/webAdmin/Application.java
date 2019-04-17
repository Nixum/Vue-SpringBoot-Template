package com.nixum.webAdmin;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEncryptableProperties
@ComponentScan({"com.nixum.*"})
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.nixum.model.dao"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}


