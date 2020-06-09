package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.AuraData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Sirghi Mihail
 */
@Api(value = "Aura API")
@Validated
public interface AuraApi {

  @ApiOperation(value = "Get user aura", nickname = "getUserAura",
      tags = {"User aura",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = AuraData.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/users/{userId}/aura",
      method = RequestMethod.GET,
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<AuraData> getUserAura(
      @ApiParam(required = true) @PathVariable("userId") @Valid @Min(1) Long userId
  );
}
