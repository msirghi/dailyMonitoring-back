package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.ProjectTaskData;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserTaskData;
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
public interface ProjectTaskApi {

  @ApiOperation(value = "Create task for project", nickname = "projectTaskCreate",
      response = ProjectData.class, tags = {"Project, Task, Create",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<TaskData> projectTaskCreate(
      @ApiParam(required = true) @RequestBody @Valid TaskData projectData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Create task for project", nickname = "projectGet",
      response = ProjectData.class, tags = {"Project, Task, Get",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/{taskId}",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<TaskData> getProjectTaskById(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("taskId") @Min(1) Long taskId
  );

  @ApiOperation(value = "Get projects tasks", nickname = "projectGetTasks",
      response = ProjectData.class, tags = {"Project, Tasks, Get",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<TaskData>> getAllTasksByProjectId(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Delete project task", nickname = "deleteProjectTask",
      response = ProjectData.class, tags = {"Project, Task, Delete",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/{taskId}",
      method = RequestMethod.DELETE,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<TaskData> deleteProjectTask(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("taskId") @Min(1) Long taskId
  );

  @ApiOperation(value = "Update project task", nickname = "UpdateProjectTask",
      response = ProjectData.class, tags = {"Project, Task, Update",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/{taskId}",
      method = RequestMethod.PUT,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<TaskData> updateProjectTask(
      @ApiParam(required = true) @RequestBody @Valid TaskData projectData,
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("taskId") @Min(1) Long taskId
  );

  @ApiOperation(value = "Complete project task", nickname = "CompleteProjectTask",
      response = ProjectData.class, tags = {"Project, Task, Complete",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/{taskId}/complete",
      method = RequestMethod.PUT,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<TaskData> markProjectTaskAsDone(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId,
      @ApiParam(required = true) @PathVariable("taskId") @Min(1) Long taskId
  );

  @ApiOperation(value = "Get all undone tasks", nickname = "GetAllUndoneTasks",
      response = ProjectData.class, tags = {"Project, Task, Get, Undode",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/inprogress",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<UserTaskData>> getAllInProgressProjectTasks(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );

  @ApiOperation(value = "Get last five done tasks", nickname = "getLastFiveDoneTasks",
      response = ProjectData.class, tags = {"Project, Task, Get, Undode",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProjectData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/projects/{projectId}/tasks/lastDone",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<TaskData>> getLastDoneTasks(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("projectId") @Min(1) Long projectId
  );
}
