package com.zypcy.springcloud.orderserviceprometheus.prometheus;

import io.prometheus.client.Gauge;

/**
 * Gauge 仪表盘
 */
public class PrometheusGauge {

    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests").help("Inprogress requests.").register();

    void processRequest() {
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();
    }
}
