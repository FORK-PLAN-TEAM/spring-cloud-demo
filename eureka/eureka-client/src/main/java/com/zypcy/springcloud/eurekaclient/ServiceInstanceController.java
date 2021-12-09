package com.zypcy.springcloud.eurekaclient;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Configuration
public class ServiceInstanceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有service信息，得到serviceId
     *
     * @return
     */
    @RequestMapping("/services")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    /**
     * 通过serviceId获取ServiceInstance
     *
     * @param serviceId
     * @return
     */
    @RequestMapping("/services/{serviceId}")
    public List<ServiceInstance> listByApplicatonName(@PathVariable String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

}
