package com.romibuzi.aggregatemetrics;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Metric aggregate for a given Day
 */
public class DailyAggregateMetric implements Serializable {
    private String metric;
    private double average;
    private double min;
    private double max;
    private double sum;
    private long count;
    private LocalDate day;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyAggregateMetric that = (DailyAggregateMetric) o;
        return Double.compare(that.getAverage(), getAverage()) == 0 &&
                Double.compare(that.getMin(), getMin()) == 0 &&
                Double.compare(that.getMax(), getMax()) == 0 &&
                Double.compare(that.getSum(), getSum()) == 0 &&
                getCount() == that.getCount() &&
                Objects.equals(getMetric(), that.getMetric()) &&
                Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetric(), getAverage(), getMin(), getMax(), getSum(), getCount(), getDay());
    }

    @Override
    public String toString() {
        return "DailyAggregateMetric{" +
                "metric='" + metric + '\'' +
                ", average=" + average +
                ", min=" + min +
                ", max=" + max +
                ", sum=" + sum +
                ", count=" + count +
                ", day=" + day +
                '}';
    }
}
