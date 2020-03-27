import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.exporters.logging.LoggingMetricExporter;
import io.opentelemetry.metrics.LongCounter;
import io.opentelemetry.metrics.LongCounter.BoundLongCounter;
import io.opentelemetry.metrics.Meter;
import io.opentelemetry.sdk.metrics.export.IntervalMetricReader;
import io.opentelemetry.sdk.metrics.export.MetricProducer;
import java.util.ArrayList;
import java.util.Collections;

public class App {
  public static void main(String[] args) {
    LoggingMetricExporter exporter = new LoggingMetricExporter();

    IntervalMetricReader intervalMetricReader =
        IntervalMetricReader.builder()
            .setExportIntervalMillis(1000)
            .setMetricExporter(exporter)
            .setMetricProducers(new ArrayList<MetricProducer>())
            .build();

    // Gets or creates a named meter instance
    Meter meter = OpenTelemetry.getMeterProvider()
        .get("instrumentation-library-name", "semver:1.0.0");

    // Build counter e.g. LongCounter
    LongCounter counter = meter
        .longCounterBuilder("processed_jobs")
        .setDescription("Processed jobs")
        .setUnit("1")
        .setLabelKeys(Collections.singletonList("Key"))
        .build();

    // It is recommended that the API user keep a reference to a Bound Counter
    BoundLongCounter someWorkCounter = counter.bind("Key", "SomeWork");

    // Record data
    someWorkCounter.add(123);


  }
}
