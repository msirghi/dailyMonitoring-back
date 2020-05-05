package com.example.dailymonitoring.configs.logger;

import org.springframework.core.env.Environment;

abstract class AbstractLogging {
  final String nl = System.getProperty("line.separator");

  Environment env;

  String generateTitle(String title) {
    return String.format(
        "%s%s---------------------------------------%s---------------------------------------",
        nl, nl, title
    );
  }
}