package com.zzycreate.test.nacos.consumer.web;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzycreate
 * @date 2019/10/1
 */
@Slf4j
@RestController
@RefreshScope
public class TestConfigController {

    @Value("${config.boolean}")
    private Boolean bool;
    @Value("${config.string}")
    private String str;
    @Value("${config.integer}")
    private Integer integer;
    @Value("${config.double}")
    private Double doub;

    @GetMapping("config")
    public Map<String, Object> config(){
        Map<String, Object> map = new HashMap<>(4);
        map.put("config.boolean", bool);
        map.put("config.string", str);
        map.put("config.integer", integer);
        map.put("config.double", doub);
        return map;
    }

}
