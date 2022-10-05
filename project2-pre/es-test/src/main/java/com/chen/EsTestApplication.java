package com.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.chen.mapper")
public class EsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsTestApplication.class, args);
	}

}
