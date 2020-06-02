package com.example.dailymonitoring;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

@SpringBootApplication
@EnableCaching
@Slf4j
@EnableScheduling
@EnableAdminServer
public class DailyMonitoringApplication {

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
