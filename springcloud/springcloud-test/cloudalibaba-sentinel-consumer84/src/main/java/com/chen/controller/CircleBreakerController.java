package com.chen.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.pojo.CommonResult;
import com.chen.pojo.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Robert V
 * @create 2022-10-20-下午2:07
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/fallback/{id}")
//    @SentinelResource(value = "fallback", fallback = "handlerFallback")
    //只负责sentinel控制台配置违规
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler")
    @SentinelResource(value = "fallback", fallback = "handlerFallback",
            blockHandler = "blockHandler", exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/payment/{id}", CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该id没有对应的记录");
        }
        return result;
    }

    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "兜底异常: " + e.getMessage(), payment);
    }

    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "限流: " + e.getMessage(), payment);
    }

}
