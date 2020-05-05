package com.example.dailymonitoring.configs.logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class Logging extends AbstractLogging {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Environment env;

  public Logging(Environment env) {
    this.env = env;
  }

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

    logger.info(this.generateTitle("Request"));
    logger.info("Ip:  {}", request.getRemoteAddr());
    logger.info("Headers: " + headers);
    logger.info("Content-Type Header: {}", request.getHeader("Content-Type"));
    logger.info("HTTP method: {}", request.getMethod());
    logger.info("Path: {}", request.getServletPath());
    logger.info("Arguments:  {}{}", Arrays.toString(joinPoint.getArgs()), nl);
  }

  @AfterReturning(pointcut = "controllerExecutionLogger()", returning = "response")
  public void afterReturningControllerAdvice(
      JoinPoint joinPoint, ResponseEntity response) throws IOException {
    logger.info(this.generateTitle("Response"));
    logger.info("Response status: " + response.getStatusCodeValue());
    logger.info("Response body: {}{}", response.getBody(), nl);
  }

  @AfterThrowing(pointcut = "controllerExecutionLogger()", throwing = "e")
  public void throwException(JoinPoint joinPoint, Throwable e) {
    e.setStackTrace(new StackTraceElement[0]);
    logger.warn("Exception", e);
  }
}
