import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.exporters.logging.LoggingMetricExporter;
import io.opentelemetry.metrics.LongCounter;
import io.opentelemetry.metrics.Meter;
import io.opentelemetry.sdk.metrics.export.IntervalMetricReader;
import io.opentelemetry.sdk.metrics.export.MetricProducer;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    LoggingMetricExporter exporter = new LoggingMetricExporter();

    IntervalMetricReader intervalMetricReader =
        IntervalMetricReader.builder()
            .setExportIntervalMillis(1000)
            .setMetricExporter(exporter)
            .setMetricProducers(new ArrayList<MetricProducer>())
            .build();

    final Meter meter = OpenTelemetry.getMeterProvider().get("test");
    final LongCounter longCounter = meter.longCounterBuilder("long").setUnit("123").build();
    longCounter.add(321);


  }
}
