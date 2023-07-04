package com.romibuzi.aggregatemetrics;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

import static org.apache.spark.sql.functions.*;

public class Aggregator {
    public static Dataset<DailyAggregateMetric> aggregateMetricsPerDay(Dataset<Metric> metrics) {
        Encoder<DailyAggregateMetric> aggregateMetricEncoder = Encoders.bean(DailyAggregateMetric.class);

        return metrics
                .groupBy(
                        window(col("timestamp"), "1 day").alias("daily_window"),
                        col("metric")
                )
                .agg(
                        round(mean("value"), 2).alias("average"),
                        min("value").alias("min"),
                        max("value").alias("max"),
                        sum("value").alias("sum"),
                        count("value").alias("count")
                )
                .withColumn("day", to_date(col("daily_window.start")))
                .drop("daily_window")
                .as(aggregateMetricEncoder);
    }
}
