package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.ProjectAlertData;
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
@Api
@Validated
public interface ProjectAlertApi {

  @ApiOperation(
      value = "Add project alert",
      nickname = "addProjectAlert",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = ProjectAlertData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectAlertData> addProjectAlert(
      @ApiParam(required = true) @RequestBody @Valid ProjectAlertData projectAlertData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(
      value = "Get project alert by id",
      nickname = "getProjectAlertId",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectAlertData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts/{alertId}",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectAlertData> getProjectAlertById(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("alertId") @Min(1) Long alertId
  );

  @ApiOperation(
      value = "Get all project alerts",
      nickname = "getAllProjectAlerts",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = List.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<ProjectAlertData>> getAllProjectAlerts(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(
      value = "Update project alert message",
      nickname = "updateProjectAlertMessage",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectAlertData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts/{alertId}",
      method = RequestMethod.PATCH,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectAlertData> updateProjectAlertMessage(
      @ApiParam(required = true) @RequestBody @Valid ProjectAlertData projectAlertData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("alertId") @Min(1) Long alertId
  );

  @ApiOperation(
      value = "Update project alert",
      nickname = "updateProjectAlert",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectAlertData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts/{alertId}",
      method = RequestMethod.PUT,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectAlertData> updateProjectAlert(
      @ApiParam(required = true) @RequestBody @Valid ProjectAlertData projectAlertData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("alertId") @Min(1) Long alertId
  );

  @ApiOperation(
      value = "Delete project alert",
      nickname = "deleteProjectAlert",
      tags = {"ProjectAlerts"}
  )
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "No content"),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/alerts/{alertId}",
      method = RequestMethod.DELETE,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<Void> deleteProjectAlert(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("alertId") @Min(1) Long alertId
  );
}
