package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.NotificationData;
import com.example.dailymonitoring.models.NotificationListData;
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
import java.util.List;

/**
 * @author Sirghi Mihail
 */
@Api(value = "Notifications")
@Validated
public interface NotificationApi {

  @ApiOperation(value = "Get notifications by user id", nickname = "getNotificationByUserId",
      tags = {"Notifications",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = NotificationData.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/notifications",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<NotificationData>> getNotificationsByUserId(
      @ApiParam(required = true) @Min(1) @PathVariable("userId") Long userId
  );

  @ApiOperation(value = "Mark notification as read", nickname = "markNotificationAsRead",
      tags = {"Notifications",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/notifications/{notificationId}",
      method = RequestMethod.PATCH,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<Void> markNotificationAsRead(
      @ApiParam(required = true) @Min(1) @PathVariable("userId") Long userId,
      @ApiParam(required = true) @Min(1) @PathVariable("notificationId") Long notificationId
  );

  @ApiOperation(value = "Mark some notifications as read", nickname = "markSomeNotificationAsRead",
      tags = {"Notifications",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/notifications",
      method = RequestMethod.PATCH,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<Void> markSomeNotificationAsRead(
      @ApiParam(required = true) @Min(1) @PathVariable("userId") Long userId,
      @ApiParam(required = true) @RequestBody @Valid NotificationListData listData
  );
}
