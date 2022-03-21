package com.jiunjiunma.manning.m2.stream.storage;

import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;

import org.apache.kafka.streams.kstream.Predicate;

public class ContainsChargeFilter implements Predicate<CanonicalKey, CanonicalValue> {
    @Override
    public boolean test(CanonicalKey canonicalKey, CanonicalValue canonicalValue) {
        if (canonicalValue != null && canonicalValue.getEvents() != null) {
            return canonicalValue.getEvents().containsKey("charging");
        }
        return false;
    }
}
