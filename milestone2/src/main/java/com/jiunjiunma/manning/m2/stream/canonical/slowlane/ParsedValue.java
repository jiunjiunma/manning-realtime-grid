package com.jiunjiunma.manning.m2.stream.canonical.slowlane;

import manning.devices.raw.m2.RawRecord;

import java.util.Map;

public class ParsedValue {
    RawRecord source;
    Exception failed;
    Map<String, String> parsed;
    boolean slow = false;

    public ParsedValue(RawRecord source) {
        this.source = source;
    }

    public ParsedValue fail(Exception cause){
        this.failed = cause;
        return this;
    }

    public ParsedValue parsed(Map<String, String> events){
        this.parsed = events;
        return this;
    }

    public ParsedValue slow(){
        this.slow = true;
        return this;
    }
}
