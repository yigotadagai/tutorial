package com.chen.controller;

import com.chen.pojo.CommonResult;
import com.chen.pojo.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Robert V
 * @create 2022-10-20-上午11:26
 */
@RestController
public class ProviderController {

    @Value("${server.port}")
    private String serverPort;

    public static Map<Long, Payment> map = new HashMap<>();

    static {
        map.put(1L, new Payment(1L, "154gad43125jhkfa8741feae5"));
        map.put(2L, new Payment(2L, "f7895akfhdjkahf895489897f"));
        map.put(2L, new Payment(3L, "80ahfkdljh89795kljahgdahh"));
    }

    @GetMapping("/payment/{id}")
    public CommonResult<Payment> paymentSql(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        CommonResult<Payment> result = new CommonResult<>(200, "from mysql, serverPort: " + serverPort, payment);
        return result;
    }

}