package com.jiunjiunma.manning.m2.api;

import io.dropwizard.lifecycle.Managed;

import java.io.Closeable;

public class CloseableManaged implements Managed {
    private Closeable closeable;

    public CloseableManaged(Closeable closeable) {
        this.closeable = closeable;
    }

    @Override
    public void start() throws Exception {
        // no-op
    }

    @Override
    public void stop() throws Exception {
        closeable.close();
    }
}
