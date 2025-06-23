package com.example.uploadservice;

import com.example.common.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UploadServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void generateJwt(){
        String token = JwtUtils.generateJwt();
        System.out.println(token);
    }
}
