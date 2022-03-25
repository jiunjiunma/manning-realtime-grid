package com.jiunjiunma.manning.m3.stream.status;

import com.jiunjiunma.manning.m3.stream.common.StreamApp;
import io.dropwizard.setup.Bootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanonicalWithStatusApp extends StreamApp<CanonicalConfWithSlowLaneAndStatus> {
    private static Logger logger = LoggerFactory.getLogger(CanonicalWithStatusApp.class);

    public static void main(String[] args) throws Exception {
        new CanonicalWithStatusApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<CanonicalConfWithSlowLaneAndStatus> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new RawToCanonicalProcessorWithDLQAndSlowLaneAndStatus(this,
                                                                                    "status",
                                                                                    "translate translation with dlq slowlane"));
    }
}