package com.jiunjiunma.manning.m1.api;

import com.codahale.metrics.health.HealthCheck;

public class HealthCheckEndpoint extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
