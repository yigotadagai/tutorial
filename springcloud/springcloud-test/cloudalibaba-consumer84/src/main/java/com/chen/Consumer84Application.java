package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Robert V
 * @create 2022-10-04-下午2:34
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Consumer84Application {
    public static void main(String[] args) {
        SpringApplication.run(Consumer84Application.class, args);
    }
}
