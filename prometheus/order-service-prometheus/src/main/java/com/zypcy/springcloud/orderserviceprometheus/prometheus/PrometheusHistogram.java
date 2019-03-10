package com.zypcy.springcloud.orderserviceprometheus.prometheus;

import io.prometheus.client.Histogram;

/**
 * 直方图 Histogram
 */
public class PrometheusHistogram {

    static final Histogram requestLatency = Histogram.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    void processRequest() {
        Histogram.Timer requestTimer = requestLatency.startTimer();
        try {
            // Your code here.
        } finally {
            requestTimer.observeDuration();
        }
    }

}
