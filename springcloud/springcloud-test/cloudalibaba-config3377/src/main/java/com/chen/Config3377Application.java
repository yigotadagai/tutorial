package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Robert V
 * @create 2022-10-04-下午3:26
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Config3377Application {
    public static void main(String[] args) {
        SpringApplication.run(Config3377Application.class, args);
    }
}
