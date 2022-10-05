package com.chen.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Robert V
 * @create 2022-10-04-下午3:28
 */
@RestController
//通过springcloud原生注解@refreshscope实现配置自动更新
@RefreshScope
@RequestMapping("/config")
public class ConfigClientController {

    /**
     * 对应nacos配置：nacos-config-dev.yaml
     */
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
