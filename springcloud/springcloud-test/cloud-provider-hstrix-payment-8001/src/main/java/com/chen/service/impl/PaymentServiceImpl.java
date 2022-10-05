package com.chen.service.impl;

import cn.hutool.core.util.IdUtil;
import com.chen.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Robert V
 * @create 2022-09-29-下午6:33
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池" + Thread.currentThread().getName() +  "paymentInfo" + id;
    }

    //超时降级演示
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @Override
    public String paymentInfo_Timeout(Integer id) {
        Integer timeout = 3;

        //测试超时情况
//        Integer timeout = 10;
        //测试异常情况
        int i = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池" + Thread.currentThread().getName() + "paymentInfo_Timeout" + id;
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //当在配置时间窗口内达到此数量的失败后，打开断路，默认20个
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //断路多久以后开始尝试是否恢复，默认5s
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //出错百分比阈值，当达到此阈值后，开始短路。默认50%
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    @Override
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new  RuntimeException("id不能为负");
        }

        //生成id
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号" + serialNumber;
    }

    //兜底方法，降级处理不成功后，我来处理
    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "payment_TimeoutHandler，系统繁忙";
    }

    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试，哭唧唧~~  id: " + id;
    }



}
