package wait.play.springbootwar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyConfig {

    @Autowired
    FileCustomMetricsExpotor metricsExpotor;

    @PostConstruct
    public void running() {
        System.out.println("----------jiajia");
        metricsExpotor.monitoring("/Users/xiaofei/Downloads", "jiajia1");
        metricsExpotor.monitoring("/Users/xiaofei/Downloads", "jiajia2");
    }

}
