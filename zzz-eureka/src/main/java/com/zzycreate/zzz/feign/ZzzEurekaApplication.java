package com.zzycreate.zzz.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka server main application
 *
 * @author zzycreate
 * @date 2019-10-31
 */
@EnableEurekaServer
@SpringBootApplication
public class ZzzEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzzEurekaApplication.class, args);
    }

}
