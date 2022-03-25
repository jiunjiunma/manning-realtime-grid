package com.jiunjiunma.manning.m3.stream.meta;

import com.jiunjiunma.manning.m3.stream.canonical.CanonicalConfiguration;
import com.jiunjiunma.manning.m3.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;

public class MetaToCanonicalApplication extends StreamApp<CanonicalConfiguration> {
    public static void main(String[] args) throws Exception {
        new MetaToCanonicalApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CanonicalConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new MetadataToCanonicalStreamProcessor(
            this, "meta", "generate meta record based on raw record"));
    }
}
