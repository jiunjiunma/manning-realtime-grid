package com.jiunjiunma.manning.m3.api;

import com.jiunjiunma.manning.m3.dao.MetaDAO;
import com.jiunjiunma.manning.m3.dao.StatusDAO;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiServer extends Application<ApiConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(ApiServer.class);

    public static void main(String[] args) throws Exception {
        new ApiServer().run(args);
    }

    @Override
    public void run(ApiConfiguration apiConfiguration, Environment environment) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, apiConfiguration.getDataSourceFactory(), "postgresql");
        MetaDAO metaDAO = jdbi.onDemand(MetaDAO.class);
        StatusDAO statusDAO = jdbi.onDemand(StatusDAO.class);

        final DeviceStatusEndpoint endpoint = new DeviceStatusEndpoint(apiConfiguration.getMetaTable(),
                                                                       apiConfiguration.getStatusTable(),
                                                                       metaDAO,
                                                                       statusDAO);

        environment.jersey().register(endpoint);
    }

}
