package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.statistics.StatisticsData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

@Api(value = "Statistics")
@Validated
public interface StatisticsApi {

  @ApiOperation(value = "Get users statistics", nickname = "getUsersStatistics",
      response = StatisticsData.class, tags = { "Statistics", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = StatisticsData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/statistics/users",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<StatisticsData> getUsersStatistics(
      @RequestParam(name = "year", required = false) @Min(2020) Long selectedYear
  );

  @ApiOperation(value = "Get tasks statistics", nickname = "getTasksStatistics",
      response = StatisticsData.class, tags = { "Statistics", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = StatisticsData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/statistics/tasks",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<StatisticsData> getTasksStatistics(
      @RequestParam(name = "year", required = false) @Min(2020) Long selectedYear
  );

  @ApiOperation(value = "Get projects statistics", nickname = "getProjectsStatistics",
      response = StatisticsData.class, tags = { "Statistics", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = StatisticsData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/statistics/projects",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<StatisticsData> getProjectsStatistics(
      @RequestParam(name = "year", required = false) @Min(2020) Long selectedYear
  );
}
