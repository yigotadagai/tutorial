package com.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Robert V
 * @create 2022-09-29-上午10:45
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule() {
        //定义为随机轮询
        return new RandomRule();
    }
}
