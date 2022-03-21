package com.jiunjiunma.manning.m2.stream.common;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class StreamApp<T extends StreamConfiguration> extends Application<T> {
    @Override
    public void run(T t, Environment environment) throws Exception {

    }
}
