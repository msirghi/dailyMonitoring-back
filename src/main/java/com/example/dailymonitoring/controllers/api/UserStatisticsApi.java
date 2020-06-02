package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.statistics.ActivityStatisticsData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.Min;

/**
 * @author Sirghi Mihail
 */
@Api
@Validated
public interface UserStatisticsApi {
  @ApiOperation(value = "Get user tasks statistics", nickname = "getUserTaskStatistics",
      response = ActivityStatisticsData.class, tags = {"UserStatistics",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ActivityStatisticsData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/statistics",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ActivityStatisticsData> getUsersStatistics(
      @PathVariable(name = "userId") @Min(1) Long userId
  );
}
