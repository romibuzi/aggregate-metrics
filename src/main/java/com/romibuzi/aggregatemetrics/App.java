package com.romibuzi.aggregatemetrics;

import org.apache.spark.sql.*;

public class App {
    private final SparkSession sparkSession;

    public App(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public Dataset<Metric> readMetrics(String inputPath) {
        Encoder<Metric> metricEncoder = Encoders.bean(Metric.class);

        return sparkSession.read()
                .option("multiline", true)
                .schema(metricEncoder.schema())
                .json(inputPath)
                .as(metricEncoder);
    }

    public void run(String inputPath, String outputPath) {
        Dataset<DailyAggregateMetric> aggregateMetrics = Aggregator.aggregateMetricsPerDay(readMetrics(inputPath));

        aggregateMetrics.repartition(functions.col("day"))
                .write()
                .partitionBy("day")
                .mode(SaveMode.Append)
                .parquet(outputPath);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: AggregateMetrics <input_json_file> <output_parquet_path>");
            System.exit(1);
        }

        SparkSession sparkSession = SparkSession.builder()
                .appName("AggregateMetrics")
                .config("spark.sql.session.timeZone", "UTC")
                .master("local")
                .getOrCreate();

        App app = new App(sparkSession);
        app.run(args[0], args[1]);
    }
}
