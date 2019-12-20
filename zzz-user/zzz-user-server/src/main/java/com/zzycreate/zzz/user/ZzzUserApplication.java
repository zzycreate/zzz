package com.zzycreate.zzz.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zzycreate
 * @date 2019.12.18
 */
@SpringBootApplication(scanBasePackages = {"com.zzycreate.zzz.user", "com.zzycreate.zf.config", "com.zzycreate.zf.feign"})
@EnableFeignClients(basePackages = {"com.zzycreate.zf.feign"})
public class ZzzUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzzUserApplication.class, args);
    }

}
