package com.romibuzi.aggregatemetrics;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    static SparkSession sparkSession;

    @BeforeAll
    static void setUp() {
        sparkSession = SparkSession.builder()
                .appName("App Tests")
                .master("local")
                .getOrCreate();
    }

    @AfterAll
    static void tearDown() {
        sparkSession.stop();
    }

    @Test
    void readMetrics() {
        // Given
        String path = getClass().getResource("/metrics_sample.json").getPath();
        App app = new App(sparkSession);

        // When
        Dataset<Metric> metrics = app.readMetrics(path);

        // Then
        assertEquals(3, metrics.count());
    }
}
