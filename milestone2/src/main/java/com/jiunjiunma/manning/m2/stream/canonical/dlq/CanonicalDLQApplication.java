package com.jiunjiunma.manning.m2.stream.canonical.dlq;

import com.jiunjiunma.manning.m2.stream.canonical.CanonicalApplication;
import com.jiunjiunma.manning.m2.stream.canonical.CanonicalConfiguration;
import com.jiunjiunma.manning.m2.stream.canonical.RawCanonicalProcessor;
import com.jiunjiunma.manning.m2.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanonicalDLQApplication extends StreamApp<CanonicalConfigurationWithDLQ> {
    private static Logger logger = LoggerFactory.getLogger(CanonicalDLQApplication.class);

    public static void main(String[] args) throws Exception {
        new CanonicalDLQApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CanonicalConfigurationWithDLQ> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new RawToCanonicalProcessorWithDLQ(this, "dlq", "translate translation with dlq"));
    }
}
