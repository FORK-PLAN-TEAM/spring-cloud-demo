package com.zypcy.springcloud.hystrixdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * HystrixDashboard 需要通过 actuator 配置暴露端口，management.endpoints.web.exposure.include=*
 * HystrixDashboard 访问地址：http://localhost:port/hystrix ，进入Dashboard界面，在文本框输入 http://localhost:port/actuator/hystrix.stream
 * 多个项目的HystrixDashboard，可以通过turbine聚合
 * 参考：https://blog.csdn.net/zhuyu19911016520/article/details/85561096
 */
@RestController
@EnableHystrixDashboard
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDemoApplication.class, args);
    }


    @RequestMapping("/getName")
    @HystrixCommand(fallbackMethod = "getNameFallback")
    public String getName(String username) throws Exception {
        if (username.equals("zhuyu")) {
            return "this is zhuyu";
        } else {
            throw new Exception();
        }
    }

    // hystrix默认1秒超时，下面的随机方法可能让线程睡3秒，从而引发hystrix熔断降级，
    // 观察dashboard界面，有百分比与圆点为红色则说明有熔断
    @HystrixCommand(fallbackMethod = "getNameFallback",
            threadPoolKey = "hystrixDemoThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    @RequestMapping("/test")
    public String test(String username) {
        randomlyRunLong();
        return "randomlyRunLong" + username;
    }

    // 1/3的机率会让线程睡3秒
    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) sleep();
    }

    //线程睡3秒
    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出错后会调用该降级方法，返回指定的信息
     *
     * @param username
     * @return
     */
    public String getNameFallback(String username) {
        return " this username is not exist ";
    }
}

