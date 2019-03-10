package com.zypcy.springcloud.orderserviceprometheus.prometheus;

import io.prometheus.client.Summary;

/**
 * Summary 摘要
 */
public class PrometheusSummary {

    private final Summary receivedBytes = Summary.build()
            .name("requests_size_bytes").help("Request size in bytes.").register();
    private final Summary requestLatency = Summary.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    ThreadLocal<Summary.Timer> threadLocal = new ThreadLocal<>();

    //开始执行
    public void processRequestStart(){
        threadLocal.set(requestLatency.startTimer());
    }

    //执行之后
    public void processRequestEnd(Object returnVal){
        receivedBytes.observe(returnVal.toString().getBytes().length);
        threadLocal.get().observeDuration();
    }

}
