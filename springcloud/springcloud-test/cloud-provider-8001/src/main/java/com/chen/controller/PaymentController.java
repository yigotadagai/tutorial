package com.chen.controller;

import com.chen.pojo.CommonResult;
import com.chen.pojo.Payment;
import com.chen.service.PaymentService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Robert V
 * @create 2022-09-27-下午2:56
 */
@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/create")
    public CommonResult<Payment> create(Payment payment) {
        int result = paymentService.create(payment);
        log.info("*******插入结果:  " + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功", result);
        } else {
            return new CommonResult(500, "插入数据失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        System.out.println("port = " + port);
        log.info("****查询结果为: " + payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功", payment);
        } else {
            return new CommonResult<>(500, "查询失败", null);
        }
    }

    @GetMapping("/feign/timeout")
    public String paymentTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }

    @GetMapping("/zipkin")
    public String paymentZipkin() {
        return "hi, I'm zipkin";
    }
}
