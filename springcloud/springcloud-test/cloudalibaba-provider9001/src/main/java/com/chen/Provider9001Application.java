package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Robert V
 * @create 2022-10-04-上午11:38
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Provider9001Application {
    public static void main(String[] args) {
        SpringApplication.run(Provider9001Application.class, args);
    }
}
