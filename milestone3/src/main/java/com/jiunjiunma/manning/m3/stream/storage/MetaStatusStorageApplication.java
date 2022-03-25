package com.jiunjiunma.manning.m3.stream.storage;

import com.jiunjiunma.manning.m3.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;

public class MetaStatusStorageApplication extends StreamApp<MetaStorageConfiguration> {
    public static void main(String[] args) throws Exception {
        new MetaStatusStorageApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MetaStorageConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new MetaStatusStorageProcessor(this, "storage", "meta status event processor"));
    }

}
