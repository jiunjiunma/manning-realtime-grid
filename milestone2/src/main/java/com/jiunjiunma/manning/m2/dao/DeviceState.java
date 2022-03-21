package com.jiunjiunma.manning.m2.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceState {

    @JsonProperty("charging")
    private int charging ;
    @JsonProperty("source")
    private String chargingSource;
    @JsonProperty("capacity")
    private int currentCapacity;

    public DeviceState() {
    }

    public int getCharging() {
        return charging;
    }

    public void setCharging(int charging) {
        this.charging = charging;
    }

    public String getChargingSource() {
        return chargingSource;
    }

    public void setChargingSource(String chargingSource) {
        this.chargingSource = chargingSource;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}
