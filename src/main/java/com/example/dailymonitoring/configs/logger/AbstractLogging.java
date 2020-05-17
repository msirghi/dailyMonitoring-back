package com.example.dailymonitoring.configs.logger;

abstract class AbstractLogging {
  final String nl = System.getProperty("line.separator");

  String generateTitle(String title) {
    return String.format(
        "%s%s---------------------------------------%s---------------------------------------",
        nl, nl, title
    );
  }
}