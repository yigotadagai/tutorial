package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Robert V
 * @create 2022-10-20-上午11:25
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Provider9004Application {
    public static void main(String[] args) {
        SpringApplication.run(Provider9004Application.class, args);
    }
}
