package com.romibuzi.aggregatemetrics;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents a single Metric
 */
public class Metric implements Serializable {
    private String metric;
    private double value;
    private Instant timestamp;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
