package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Robert V
 * @create 2022-10-20-下午2:04
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Consumer84Application {
    public static void main(String[] args) {
        SpringApplication.run(Consumer84Application.class, args);
    }
}
