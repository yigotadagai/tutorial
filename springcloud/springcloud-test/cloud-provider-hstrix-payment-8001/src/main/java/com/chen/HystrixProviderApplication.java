package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Robert V
 * @create 2022-09-29-下午6:21
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class HystrixProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixProviderApplication.class, args);
    }
}
