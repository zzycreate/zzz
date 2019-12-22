package com.zzycreate.zzz.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@SpringBootApplication(scanBasePackages = {"com.zzycreate.zzz.application", "com.zzycreate.zf.config"})
@EnableFeignClients(basePackages = "com.zzycreate.zzz.application")
public class ZzzAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzzAppApplication.class, args);
    }

}
