Metrics Aggregation batch job with Spark
======

Spark application to aggregate a given JSON file of metrics into a processed dataset in Parquet format.

## Requirements

- Java >= 11
- IDE like IntelliJ or Maven command line

## Aggregations

Aggregations on metrics are done at a daily level and store:
- average of values for each metric
- minimum value
- maximum value
- sum of values
- count of values

sum and count are kept in the generated processed Parquet to be able to compute the average on a larger window than a day (month or year).

## Steps

With a given JSON file having the following structure:

```bash
cat metrics.json

[
  {
    "metric": "temperature",
    "value": 91,
    "timestamp": "2023-05-24T18:20:00.000Z"
  },
  {
    "metric": "precipitation",
    "value": 0.9,
    "timestamp": "2023-05-23T07:27:19.000Z"
  },
  {
    "metric": "temperature",
    "value": 80,
    "timestamp": "2023-05-25T15:25:00.000Z"
  }
]
```

Package the Spark application:

```
mvn package
```

Then run the Spark application with `spark-submit`:

```
spark-submit --master "local[2]" target/aggregate-metrics-1.0.jar metrics.json processed.parquet 
```

1st argument is the input path of JSON metrics and 2nd argument is the path of the generated Parquet that will contains aggregate metrics.

The other way is to launch directly `App.main()` from the IDE with these arguments. (On IntelliJ enable option `Add dependencies with 'provided' scope to the classpath`) 

## Tests

```
mvn test
```
