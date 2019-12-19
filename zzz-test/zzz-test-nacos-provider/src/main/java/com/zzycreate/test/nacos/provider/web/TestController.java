package com.zzycreate.test.nacos.provider.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzycreate
 * @date 2019/9/28
 */
@Slf4j
@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name);
        return "hello " + name + ", this port is " + port;
    }

}
