package com.zzycreate.test.nacos.consumer.feign;

import com.zzycreate.test.nacos.consumer.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zzycreate
 * @date 2019/10/1
 */
@FeignClient(ServiceNameConstant.NACOS_PROVIDER)
public interface ZzzTestNacosProviderFeign {

    @GetMapping("/hello")
    String hello(@RequestParam(name = "name") String name);

}
