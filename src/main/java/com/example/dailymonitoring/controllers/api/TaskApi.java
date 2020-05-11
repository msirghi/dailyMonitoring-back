package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "Tasks")
@Validated
public interface TaskApi {

  @ApiOperation(value = "Create Task", nickname = "taskCreate",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> taskCreate(
      @ApiParam(required = true) @RequestBody @Valid TaskData taskData,
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );

  @ApiOperation(value = "Get Task", nickname = "taskGet",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<TaskData>> taskGet(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );

  @ApiOperation(value = "Get particular Task", nickname = "particularTaskGet",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks/{taskId}",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> particularTaskGet(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("taskId") @Valid @Min(1) Long taskId
  );

  @ApiOperation(value = "Delete Particular Task", nickname = "particularTaskDelete",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks/{taskId}",
      method = RequestMethod.DELETE,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> taskDelete(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId,
      @ApiParam(required = true) @PathVariable("taskId") @Valid @Min(1) Long taskId
  );


  @ApiOperation(value = "Get Done Tasks", nickname = "doneTaskGet",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks/done",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<TaskData>> doneTasksGet(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );

  @ApiOperation(value = "Get In Progress Tasks", nickname = "inProgressTaskGet",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks/inProgress",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> inProgressTasksGet(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );

  @ApiOperation(value = "Get Undone Tasks", nickname = "undoneTaskGet",
      response = TaskData.class, tags = {"Tasks",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/tasks/undone",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> undoneTasksGet(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );

  //  @ApiOperation(value = "Update Particular Task", nickname = "particularTaskUpdate",
  //      response = TaskData.class, tags = {"Update, Task , Particular",})
  //  @ApiResponses(value = {
  //      @ApiResponse(code = 200, message = "Ok", response = TaskData.class),
  //      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
  //      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
  //      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
  //      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
  //      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  //  @RequestMapping(
  //      value = "/users/{userId}/tasks/{taskId}",
  //      method = RequestMethod.PUT,
  //      produces = "application/json;charset=utf-8"
  //  )
  //  ResponseEntity<?> taskUpdate(
  //      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId ,
  //      @ApiParam(required = true) @PathVariable("taskId") @Min(1) Long taskId
  //  );
  //


}
