package com.example.dailyMonitoring.controllers.api;

import com.example.dailyMonitoring.models.EmailData;
import com.example.dailyMonitoring.models.Error;
import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.UsernameData;
import com.example.dailyMonitoring.respositories.UserRepository;
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
  ResponseEntity<?> userGet(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Delete User", nickname = "userDelete",
          response = UserData.class, tags = {"Delete, User",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "users/{userId}",
          method = RequestMethod.DELETE,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userDelete(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Update User", nickname = "userUpdate",
          response = UserData.class, tags = {"Update, User",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "users/{userId}",
          method = RequestMethod.PUT,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdate(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId, @RequestBody @Valid UserData userData
  );

  @ApiOperation(value = "Update User Password Only", nickname = "userUpdatePasswordOnly",
          response = UserData.class, tags = {"Update, User,Password",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "users/{userId}/resetPwd",
          method = RequestMethod.PUT,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdatePasswordOnly(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId, @RequestBody @Valid UserData userData
  );


  @ApiOperation(value = "Update User Email Only", nickname = "userUpdateEmailOnly",
          response = UserData.class, tags = {"Update, User,Email",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "users/{userId}/resetEmail",
          method = RequestMethod.PUT,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdateEmailOnly(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId, @RequestBody @Valid EmailData emailData
  );



  @ApiOperation(value = "Update User Username Only", nickname = "userUpdateUsernameOnly",
          response = UserData.class, tags = {"Update, User,Username",})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Ok", response = UserData.class),
          @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
          @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
          @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
          @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
          @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
          value = "users/{userId}/resetUsrName",
          method = RequestMethod.PUT,
          produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdateUsernameOnly(
          @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId, @RequestBody @Valid UsernameData usernameData
  );
  //    TODO: 25.03.2020
  /*
    username
    fullName
    email

    users/{id}/resetPwd
   */
}
