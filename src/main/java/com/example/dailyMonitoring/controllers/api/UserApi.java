package com.example.dailyMonitoring.controllers.api;

import com.example.dailyMonitoring.models.Error;
import com.example.dailyMonitoring.models.UserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api
@Validated
public interface UserApi {
  @ApiOperation(value = "Create User", nickname = "userCreate",
          response = UserData.class, tags = {"Create, User",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "/users",
          method = RequestMethod.POST,
          consumes = "application/json;charset=utf-8",
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userCreate(
          @ApiParam(required = true) @RequestBody @Valid UserData userData
  );

  @ApiOperation(value = "Get User", nickname = "userGet",
          response = UserData.class, tags = {"Get, User",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "/users/{userId}",
          method = RequestMethod.GET,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<UserData> userGet(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );
}
