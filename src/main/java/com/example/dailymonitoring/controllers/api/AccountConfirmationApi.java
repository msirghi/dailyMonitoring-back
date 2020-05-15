package com.example.dailymonitoring.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "Account confirmation")
@Validated
public interface AccountConfirmationApi {

  @ApiOperation(value = "Confirm account registration", nickname = "confirmAccountRegistration",
      tags = {"Account confirmation",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/registrationConfirm",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  void activateAccount(
      @RequestParam(name = "token") String token,
      HttpServletResponse httpServletResponse
  );
}
