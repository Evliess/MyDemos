package wait.play.springbootwar;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileCustomMetricsExpotor {

  private MeterRegistry registry;
  public FileCustomMetricsExpotor (MeterRegistry registry) {
    this.registry = registry;
  }


  public void monitoring(String path, String metricsName) {
    final File file = new File(path);
    file.setReadable(true);
    Gauge.builder(metricsName, file::getFreeSpace).
        tag("freespace", file.getName()).strongReference(true).
        description("file free space").register(registry);
  }

}
