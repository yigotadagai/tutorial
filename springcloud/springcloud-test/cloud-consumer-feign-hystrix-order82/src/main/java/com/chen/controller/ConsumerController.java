package com.chen.controller;

import com.chen.service.ConsumerService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Robert V
 * @create 2022-09-29-下午8:50
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "consumerGlobalFallback")
public class ConsumerController {

    @Resource
    private ConsumerService consumerService;

    @GetMapping("/ok/{id}")
    public String consumerInfo_OK(@PathVariable("id") Integer id) {
        String result = consumerService.paymentInfo_OK(id);
        log.info("**************result" + result);
        return result;
    }

    //指定兜底方法
//    @HystrixCommand(fallbackMethod = "consumer_TimeoutHandler", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")//超过1.5秒就降级自己
//    })

    //使用默认的兜底方法
    @HystrixCommand
    @GetMapping("/timeout/{id}")
    public String consumerInfo_Timeout(@PathVariable("id") Integer id) {
//        int age = 1 / 0;
        String result = consumerService.paymentInfo_Timeout(id);
        log.info("***********result" + result);
        return result;
    }

    //指定兜底方法
    public String consumer_TimeoutHandler(Integer i) {
        return "消费者83，对方支付系统繁忙，请稍后再试";
    }

    //全局兜底方法
    public String consumerGlobalFallback() {
        return "Global异常处理，请稍后再试";
    }

}
