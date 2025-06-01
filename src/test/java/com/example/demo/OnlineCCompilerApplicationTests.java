package com.example.demo;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.common.AliyunOSSOperator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.mapper.CompileTaskMapper;



@SpringBootTest(classes = OnlineCCompilerApplication.class)
@MapperScan("com.example.demo.dao.mapper") 
class OnlineCCompilerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void helloTest(){
		System.out.println("hello");
	}

	@Autowired
	private CompileTaskMapper compileTaskMapper;
	@Test
	public void compileTaskMapperTest(){
		System.out.println(compileTaskMapper.findAll());;
	}

	@Test
	public void generateJwt(){
		String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aGFybWFy").compact();
		System.out.println(jwt);
	}

	@Test
	public void testOSS(){
        try {
            String url = AliyunOSSOperator.upload("hello".getBytes(),"hello.txt");
			System.out.println(url);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
