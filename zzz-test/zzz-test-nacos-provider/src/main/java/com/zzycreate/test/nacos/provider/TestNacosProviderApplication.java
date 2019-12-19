package com.zzycreate.test.nacos.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zzycreate
 * @date 2019/9/28
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TestNacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNacosProviderApplication.class, args);
    }

}
