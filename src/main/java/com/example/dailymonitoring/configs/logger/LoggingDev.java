package com.example.dailymonitoring.configs.logger;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Profile("dev")
@Log4j2
public class LoggingDev extends AbstractLogging {

  @Pointcut("execution(public * com.example.dailymonitoring.controllers.*.*(..))")
  public void controllerExecutionLogger() {
  }

  @Before("controllerExecutionLogger()")
  public void beforeControllerExecutionAdvice(JoinPoint joinPoint) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();

    Map<String, String> headers = new HashMap<>();
    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      headers.put(key, value);
    }

    log.info(this.generateTitle("Request"));
    log.info("Ip:  {}", request.getRemoteAddr());
    log.info("Headers: " + headers);
    log.info("Content-Type Header: {}", request.getHeader("Content-Type"));
    log.info("HTTP method: {}", request.getMethod());
    log.info("Path: {}", request.getServletPath());
    log.info("Arguments:  {}{}", Arrays.toString(joinPoint.getArgs()), nl);
  }

  @AfterReturning(pointcut = "controllerExecutionLogger()", returning = "response")
  public void afterReturningControllerAdvice(
      JoinPoint joinPoint, ResponseEntity response) throws IOException {
    log.info(this.generateTitle("Response"));
    log.info("Response status: " + response.getStatusCodeValue());
    log.info("Response body: {}{}", response.getBody(), nl);
  }

  @AfterThrowing(pointcut = "controllerExecutionLogger()", throwing = "e")
  public void throwException(JoinPoint joinPoint, Throwable e) {
    e.setStackTrace(new StackTraceElement[0]);
    log.warn("Exception", e);
  }
}
