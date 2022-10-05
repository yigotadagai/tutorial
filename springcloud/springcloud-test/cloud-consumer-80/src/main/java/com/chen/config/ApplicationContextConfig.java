package com.chen.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Robert V
 * @create 2022-09-27-下午3:52
 */
@SpringBootConfiguration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced //通过注册中心获取服务列表，远程调用采用负载均衡
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
