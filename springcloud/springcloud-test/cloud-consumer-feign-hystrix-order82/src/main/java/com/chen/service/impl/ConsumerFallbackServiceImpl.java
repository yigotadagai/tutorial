package com.chen.service.impl;

import com.chen.service.ConsumerService;
import org.springframework.stereotype.Service;

/**
 * @author Robert V
 * @create 2022-09-30-上午9:05
 *
 * 客户端与服务端解耦合，定义一个实现类处理接口的异常，与服务端分开
 */
@Service
public class ConsumerFallbackServiceImpl implements ConsumerService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "---------consumerService is fallback paymentInfo_OK,哭唧唧";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "--------------consumerService is fallback timeout 哭唧唧";
    }
}
