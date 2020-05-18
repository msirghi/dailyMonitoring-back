package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.statistics.ProjectTaskStatisticsData;
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

@Api(value = "Projects")
@Validated
public interface ProjectApi {

  @ApiOperation(value = "Create project", nickname = "projectCreate",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/projects",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectData> projectCreate(
      @ApiParam(required = true) @RequestBody @Valid ProjectData projectData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Get project", nickname = "getProject",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/projects/{projectId}",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectData> getProjectById(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Get list of projects", nickname = "getProjectsByUser",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/projects",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<ProjectData>> getProjectsByUser(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Delete project by id", nickname = "deleteProject",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}",
      method = RequestMethod.DELETE,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectData> projectDelete(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Update project by id", nickname = "updateProject",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}",
      method = RequestMethod.PUT,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectData> projectUpdate(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @RequestBody @Valid ProjectData projectData
  );

  @ApiOperation(value = "Update project name", nickname = "updateProjectName",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/projects/{projectId}/name",
      method = RequestMethod.PUT,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<ProjectData> updateProjectName(
      @ApiParam(required = true) @RequestBody @Valid ProjectData projectData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Get task statistics", nickname = "getTaskStatistics",
      response = ProjectData.class, tags = { "Projects", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/projects/{projectId}/statistics/tasksByUser",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<ProjectTaskStatisticsData>> getTasksStatistics(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );
}
