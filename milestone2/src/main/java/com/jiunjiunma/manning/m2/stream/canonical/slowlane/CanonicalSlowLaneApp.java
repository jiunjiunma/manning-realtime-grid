package com.jiunjiunma.manning.m2.stream.canonical.slowlane;

import com.jiunjiunma.manning.m2.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanonicalSlowLaneApp extends StreamApp<CanonicalConfWithSlowLane> {
    private static Logger logger = LoggerFactory.getLogger(CanonicalSlowLaneApp.class);

    public static void main(String[] args) throws Exception {
        new CanonicalSlowLaneApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<CanonicalConfWithSlowLane> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new RawToCanonicalProcessorWithDLQAndSlowLane(this,
                                                                           "slowlane",
                                                                           "translate translation with dlq slowlane"));
    }
}