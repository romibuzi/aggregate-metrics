package com.romibuzi.aggregatemetrics;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class AggregatorTest {
    static SparkSession sparkSession;

    @BeforeAll
    static void setUp() {
        sparkSession = SparkSession.builder()
                .appName("Aggregator Tests")
                .master("local")
                .getOrCreate();
    }

    @AfterAll
    static void tearDown() {
        sparkSession.stop();
    }

    @Test
    void aggregateMetricsPerDay() {
        // Given
        Metric metric1 = new Metric();
        metric1.setMetric("precipitation");
        metric1.setValue(0.5);
        metric1.setTimestamp(Instant.parse("2022-06-05T14:23:32.000Z"));

        Metric metric2 = new Metric();
        metric2.setMetric("temperature");
        metric2.setValue(88.0);
        metric2.setTimestamp(Instant.parse("2022-06-04T00:01:00.000Z"));

        Metric metric3 = new Metric();
        metric3.setMetric("temperature");
        metric3.setValue(89.0);
        metric3.setTimestamp(Instant.parse("2022-06-04T12:01:30.000Z"));

        Dataset<Metric> metrics = sparkSession.createDataset(
                List.of(metric1, metric2, metric3),
                Encoders.bean(Metric.class)
        );

        // When
        Dataset<DailyAggregateMetric> aggregateMetrics = Aggregator.aggregateMetricsPerDay(metrics).orderBy("metric");

        // Then
        DailyAggregateMetric aggregatePrecipitation = new DailyAggregateMetric();
        aggregatePrecipitation.setDay(LocalDate.of(2022, 6, 5));
        aggregatePrecipitation.setMetric("precipitation");
        aggregatePrecipitation.setCount(1);
        aggregatePrecipitation.setMin(0.5);
        aggregatePrecipitation.setMax(0.5);
        aggregatePrecipitation.setSum(0.5);
        aggregatePrecipitation.setAverage(0.5);

        DailyAggregateMetric aggregateTemperature = new DailyAggregateMetric();
        aggregateTemperature.setDay(LocalDate.of(2022, 6, 4));
        aggregateTemperature.setMetric("temperature");
        aggregateTemperature.setCount(2);
        aggregateTemperature.setMin(88.0);
        aggregateTemperature.setMax(89.0);
        aggregateTemperature.setSum(88.0 + 89.0);
        aggregateTemperature.setAverage((88.0 + 89.0) / 2);

        assertIterableEquals(
                List.of(aggregatePrecipitation, aggregateTemperature),
                aggregateMetrics.collectAsList()
        );
    }
}
