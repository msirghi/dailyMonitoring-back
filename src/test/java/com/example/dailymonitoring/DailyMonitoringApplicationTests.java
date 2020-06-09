package com.example.dailymonitoring;

import com.example.dailymonitoring.controllers.EmailTemplateControllerTest;
import com.example.dailymonitoring.controllers._zProjectAlertControllerTest;
import com.example.dailymonitoring.controllers.ProjectControllerTest;
import com.example.dailymonitoring.controllers.ProjectTaskControllerTest;
import com.example.dailymonitoring.controllers.ProjectUserControllerTest;
import com.example.dailymonitoring.controllers.StatisticsControllerTest;
import com.example.dailymonitoring.controllers.UserControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
    EmailTemplateControllerTest.class,
    ProjectControllerTest.class,
    ProjectTaskControllerTest.class,
    ProjectUserControllerTest.class,
    StatisticsControllerTest.class,
    UserControllerTest.class,
    _zProjectAlertControllerTest.class
})
class DailyMonitoringApplicationTests {

  @Test
  void contextLoads() {
  }

}
