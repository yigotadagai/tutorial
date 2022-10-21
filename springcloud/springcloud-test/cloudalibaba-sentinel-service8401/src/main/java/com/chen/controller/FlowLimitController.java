package com.chen.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.handler.CustomerBlockHandler;
import com.chen.pojo.CommonResult;
import com.chen.pojo.Payment;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Robert V
 * @create 2022-10-06-下午7:39
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "--------testA----------";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t ...testB");
        return "------------testB----------";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT");
        return "-----------testD---------";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE测试异常比例");
        int age = 10 / 0;
        return "---------testE";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
//        int age = 10/ 0;
        return "-----------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "-------------deal_testHotKey o(π~~π)o";
    }

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200,"按资源名称限流测试okk",
                new Payment(2020L, "serial_1"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t服务不可用");
    }

    @GetMapping("/rateLimit/Url")
    public CommonResult byUrl() {
        return new CommonResult(200, "按照url限流测试opd",
                new Payment(2020L, "serial_2"));
    }

    @GetMapping("/rateLimit/customerHandler") // TODO: 2022/10/20 自定义兜底方法没起作用
    @SentinelResource(value = "customerHandler", blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handleException1")
    public CommonResult customerHandler() {
        return new CommonResult(200, "客户自定义的限流操作测试", new Payment(2022L, "serial_3"));
    }
}
