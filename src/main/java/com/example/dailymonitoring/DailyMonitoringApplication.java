package com.example.dailymonitoring;

import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class DailyMonitoringApplication {

  private static final Logger log = LoggerFactory.getLogger(DailyMonitoringApplication.class);

  public static void main(String[] args) throws Exception {
    SpringApplication app = new SpringApplication(DailyMonitoringApplication.class);
    ApplicationContext context = app.run(args);
    Environment env = context.getEnvironment();
    String port = env.getProperty("server.port", "8080");
    String schema = env.getProperty("server.ssl.enabled", Boolean.class, Boolean.FALSE)
        ? "https" : "http";
    log.info("Local: \t\t{}://127.0.0.1:{}", schema, port);
    log.info("External: \t{}://{}:{}", schema, InetAddress.getLocalHost().getHostAddress(), port);
  }

}
