package com.zzycreate.test.nacos.consumer.web;

import com.zzycreate.test.nacos.consumer.constant.ServiceNameConstant;
import com.zzycreate.test.nacos.consumer.feign.ZzzTestNacosProviderFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author zzycreate
 * @date 2019/9/29
 */
@Slf4j
@RestController
public class TestController {

    /*
     * 使用 RestTemplate 进行 nacos 服务访问
     */

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(@RequestParam String name) {
        // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceNameConstant.NACOS_PROVIDER);
        String url = serviceInstance.getUri() + "/hello?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke : " + url + ", return : " + result;
    }

    @GetMapping("/test1")
    public String test1(@RequestParam String name) {
        // 直接访问服务名称
        String url = "http://" + ServiceNameConstant.NACOS_PROVIDER + "/hello?name=" + name;
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke : " + url + ", return : " + result;
    }

    /*
     * 使用 spring5 WebClient 进行 nacos 服务访问
     */

    @Autowired
    WebClient.Builder webClientBuilder;

    @GetMapping("/test2")
    public Mono<String> test2(@RequestParam String name) {
        String url = "http://" + ServiceNameConstant.NACOS_PROVIDER + "/hello?name=" + name;
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

    /*
     * 使用 Feign 进行 nacos 服务访问
     */

    @Autowired
    private ZzzTestNacosProviderFeign zzzTestNacosProviderFeign;

    @GetMapping("/test3")
    public String test3(@RequestParam String name) {
        // 直接访问服务名称
        String hello = this.zzzTestNacosProviderFeign.hello(name);
        return "return : " + hello;
    }

}
