package com.zypcy.springcloud.orderserviceprometheus.prometheus;

import io.prometheus.client.Counter;

/**
 * Counter 计数器
 */
public class PrometheusCounter {

    private String labelName;

    public PrometheusCounter() {

    }

    public PrometheusCounter(String _labelName) {
        this.labelName = _labelName;
    }

    public Counter getCounter() {
        if (!labelName.equals("")) {
            return Counter.build().name("requests_total").labelNames(labelName).help("Total requests.").register();
        } else {
            return requests;
        }
    }

    private final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.").register();

    //请求累加
    public void increment() {
        requests.inc();
    }
}
