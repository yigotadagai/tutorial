package com.chen.service;

import com.chen.service.impl.ConsumerFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Robert V
 * @create 2022-09-29-下午8:50
 */
@Component
@FeignClient(value = "CLOUD-HYSTIRX-SERVICE", fallback = ConsumerFallbackServiceImpl.class)
public interface ConsumerService {

    @GetMapping("/payment/hystrix/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable("id") Integer id);
}
