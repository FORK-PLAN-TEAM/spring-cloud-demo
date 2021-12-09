package com.zypcy.springcloud.orderserviceprometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServicePrometheusApplication {


    public static void main(String[] args) {
        SpringApplication.run(OrderServicePrometheusApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }

    //@Autowired
    //PrometheusMeterRegistry registry;
//	@Bean
//	public Counter getCounter() {
//		Counter counter = Counter.builder("requests_total")
//				.tags("status", "success")
//				.description("Number of successful goods rank sync")
//				.register(registry);
//		return counter;
//	}
}
