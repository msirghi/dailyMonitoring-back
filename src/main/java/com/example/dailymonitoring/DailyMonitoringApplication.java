package com.example.dailymonitoring;

import java.net.InetAddress;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
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

//  @Bean
//  public ServletWebServerFactory servletContainer() {
//    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//      @Override
//      protected void postProcessContext(Context context) {
//        SecurityConstraint securityConstraint = new SecurityConstraint();
//        securityConstraint.setUserConstraint("CONFIDENTIAL");
//        SecurityCollection collection = new SecurityCollection();
//        collection.addPattern("/*");
//        securityConstraint.addCollection(collection);
//        context.addConstraint(securityConstraint);
//      }
//    };
//    tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//    return tomcat;
//  }
//
//  /*
//  We need to redirect from HTTP to HTTPS. Without SSL, this application used
//  port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
//  redirected to HTTPS on 8443.
//   */
//  private Connector httpToHttpsRedirectConnector() {
//    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//    connector.setScheme("http");
//    connector.setPort(8082);
//    connector.setSecure(false);
//    connector.setRedirectPort(8443);
//    return connector;
//  }
}
