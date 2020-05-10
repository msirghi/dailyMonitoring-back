package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.ProjectUserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api
@Validated
public interface ProjectUserApi {

  @ApiOperation(value = "Add user in project", nickname = "addProjectUser",
      response = ProjectUserData.class, tags = {"Project, User, Add",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectUserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/projects/addUser",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectUserData> addProjectUser(
      @ApiParam(required = true) @RequestBody @Valid ProjectUserData projectUserData
  );

  @ApiOperation(value = "Delete a user from the project", nickname = "deleteProjectUser",
      response = ProjectUserData.class, tags = {"Project, User, Delete",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/projects/{projectId}/users/{userId}",
      method = RequestMethod.DELETE,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<Void> deleteUserFromProject(
      @ApiParam(required = true) @Min(1) @PathVariable("projectId") Long projectId,
      @ApiParam(required = true) @Min(1) @PathVariable("userId") Long userId
  );

  @ApiOperation(value = "Get all project users", nickname = "getAllProjectUsers",
      response = ProjectUserData.class, tags = {"Project, User, Get, All",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectUserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "users/{userId}/projects/{projectId}/users",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<ProjectUserData>> getAllProjectUsers(
      @ApiParam(required = true) @Min(1) @PathVariable("userId") Long userId,
      @ApiParam(required = true) @Min(1) @PathVariable("projectId") Long projectId
  );
}
