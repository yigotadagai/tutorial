package com.chen.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Robert V
 * @create 2022-09-25-上午10:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ESConfig {
    private String host;
    private Integer port;

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
    }
}
