package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.auth.AuthenticationRequestData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Api
@CrossOrigin(allowedHeaders = "*", origins = "*")
public interface AuthApi {

  @ApiOperation(value = "Authenticate User", nickname = "userAuthenticate",
      response = UserData.class, tags = {"Authenticate, User",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = UserData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/authenticate",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequestData authenticationRequestData,
      HttpServletResponse response
  );
}