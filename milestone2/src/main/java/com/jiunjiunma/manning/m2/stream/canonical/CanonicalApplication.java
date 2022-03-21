package com.jiunjiunma.manning.m2.stream.canonical;

import com.jiunjiunma.manning.m2.api.ApiServer;
import com.jiunjiunma.manning.m2.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanonicalApplication extends StreamApp<CanonicalConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(CanonicalApplication.class);

    public static void main(String[] args) throws Exception {
        new CanonicalApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CanonicalConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new RawCanonicalProcessor(this, "canonical", "translate raw to canonical format"));
    }
}
