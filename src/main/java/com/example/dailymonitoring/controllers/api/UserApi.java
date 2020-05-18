package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api(value = "Users")
@Validated
public interface UserApi {

  @ApiOperation(value = "Create User", nickname = "userCreate",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/users",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userCreate(
      @ApiParam(required = true) @RequestBody @Valid UserData userData,
      HttpServletRequest request
  );

  @ApiOperation(value = "Get User", nickname = "userGet",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/users/{userId}",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userGet(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Delete User", nickname = "userDelete",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}",
      method = RequestMethod.DELETE,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userDelete(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId
  );

  @ApiOperation(value = "Update User", nickname = "userUpdate",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}",
      method = RequestMethod.PUT,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdate(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @RequestBody @Valid UserData userData
  );

  @ApiOperation(value = "Update User Password Only", nickname = "userUpdatePasswordOnly",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/resetPwd",
      method = RequestMethod.PUT,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdatePasswordOnly(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @RequestBody @Valid PasswordData passwordData
  );

  @ApiOperation(value = "Update User Email Only", nickname = "userUpdateEmailOnly",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/resetEmail",
      method = RequestMethod.PUT,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdateEmailOnly(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @RequestBody @Valid EmailData emailData
  );

  @ApiOperation(value = "Update User Username Only", nickname = "userUpdateUsernameOnly",
      response = UserData.class, tags = { "Users", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "users/{userId}/resetUsrName",
      method = RequestMethod.PUT,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> userUpdateUsernameOnly(
      @ApiParam(required = true) @PathVariable("userId") @Min(1) Long userId,
      @RequestBody @Valid UsernameData usernameData
  );
}
