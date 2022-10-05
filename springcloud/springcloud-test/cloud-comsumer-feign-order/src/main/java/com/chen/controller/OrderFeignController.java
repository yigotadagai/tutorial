package com.chen.controller;

import com.chen.pojo.CommonResult;
import com.chen.pojo.Payment;
import com.chen.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Robert V
 * @create 2022-09-29-下午2:20
 */
@RestController
@RequestMapping("/consumer")
public class OrderFeignController {
    /**
     * 调用远程的微服接口
     */
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPayment(id);
    }


    @GetMapping("/feign/timeout")
    public String paymentTimeout() {
        return paymentFeignService.paymentTimeout();
    }
}
