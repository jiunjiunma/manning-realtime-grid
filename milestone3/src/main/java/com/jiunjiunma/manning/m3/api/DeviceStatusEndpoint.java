package com.jiunjiunma.manning.m3.api;

import com.jiunjiunma.manning.m3.dao.MetaDAO;
import com.jiunjiunma.manning.m3.dao.StatusDAO;
import com.jiunjiunma.manning.m3.dao.StatusRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceStatusEndpoint {
    private final String metaTable;
    private final String statusTable;
    private final MetaDAO metaDAO;
    private final StatusDAO statusDAO;

    public DeviceStatusEndpoint(String metaTable, String statusTable, MetaDAO metaDAO, StatusDAO statusDAO) {
        this.metaTable = metaTable;
        this.statusTable = statusTable;
        this.metaDAO = metaDAO;
        this.statusDAO = statusDAO;
    }


    @GET
    @Path("{uuid}/badMessageCount")
    public int badMessages(@PathParam("uuid") String uuid) {
        return statusDAO.badMessages(statusTable, uuid);
    }

    @GET
    @Path("{uuid}")
    @Produces("application/json")
    public StatusRecord getMessageStatusInfo(@PathParam("uuid") String uuid) {
        return statusDAO.selectLatest(statusTable, uuid)
            .orElseThrow(()->new NotFoundException("device " + uuid + "not found"));
    }

    @GET
    @Path("deviceCount")
    public int numberOfConnectedDevices() {
        return metaDAO.numberOfDevices(metaTable);
    }
}
