package com.chen.dao;

import com.chen.pojo.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Robert V
 * @create 2022-09-27-下午2:46
 */
@Component
public interface PaymentDao {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
