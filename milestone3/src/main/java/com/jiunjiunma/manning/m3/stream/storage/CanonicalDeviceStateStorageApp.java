package com.jiunjiunma.manning.m3.stream.storage;

import com.jiunjiunma.manning.m3.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;

public class CanonicalDeviceStateStorageApp extends StreamApp<StorageConfiguration> {
    public static void main(String[] args) throws Exception {
        new CanonicalDeviceStateStorageApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<StorageConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new CanonicalStorageProcessor(this, "storage", "charging event processor"));
    }

}
