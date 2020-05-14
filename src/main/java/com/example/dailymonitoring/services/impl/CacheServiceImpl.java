package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

  private final CacheManager cacheManager;

  public CacheServiceImpl(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  // every day at 6am
  @Scheduled(cron = "0 0 6 * * *")
  public void scheduleCacheClear() {
    log.info("Cache clear started.");
    evictAllCaches();
  }

  @Override
  public void evictAllCaches() {
    cacheManager
        .getCacheNames()
        .forEach(cacheName -> {
          cacheManager.getCache(cacheName).clear();
          log.info("Cache " + cacheName + " is cleared.");
        });
  }
}
