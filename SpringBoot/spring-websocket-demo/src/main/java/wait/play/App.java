package wait.play;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

/**
 * Hello world!
 */

@SpringBootApplication
@ComponentScan("wait.play")
public class App {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(App.class);
    app.run(args);
  }
}
