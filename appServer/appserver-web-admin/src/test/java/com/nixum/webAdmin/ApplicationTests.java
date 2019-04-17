package com.nixum.webAdmin;

import com.alibaba.fastjson.JSONObject;
import com.nixum.common.response.ResultGenerator;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("demo");
        System.out.println("==================");
        System.out.println(result);
        System.out.println("==================");
    }


    @Test
    public void testJson() {
        JSONObject responseJSONObject = (JSONObject) JSONObject.toJSON(ResultGenerator.genUnauthorizedResult("登录过时了，请重新登录"));
        System.out.println(responseJSONObject.toJSONString());
    }

}
