package com.zzycreate.test.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zzycreate
 * @date 2019/9/28
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class TestNacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNacosConsumerApplication.class, args);
    }

}
