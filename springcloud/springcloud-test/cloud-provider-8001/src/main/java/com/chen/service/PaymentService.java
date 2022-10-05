package com.chen.service;

import com.chen.pojo.Payment;
import org.springframework.stereotype.Service;

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(Long id);
}
