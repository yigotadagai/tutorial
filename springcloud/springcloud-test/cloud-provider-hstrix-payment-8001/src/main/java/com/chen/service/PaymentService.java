package com.chen.service;

/**
 * @author Robert V
 * @create 2022-09-29-下午6:24
 */
public interface PaymentService {
    String paymentInfo_OK(Integer id);

    String paymentInfo_Timeout(Integer id);

    String paymentCircuitBreaker(Integer id);
}
