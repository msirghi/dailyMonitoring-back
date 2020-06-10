package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.Error;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sirghi Mihail
 */
@Api(value = "Images")
@Validated
public interface ImageApi {

  @ApiOperation(value = "Get image by name", nickname = "getImageByName", tags = {"Images",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = byte[].class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class)})
  @RequestMapping(
      value = "/images/{name}",
      method = RequestMethod.GET,
      produces = MediaType.IMAGE_JPEG_VALUE
  )
  ResponseEntity<byte[]> getImageByName(
      @ApiParam(required = true) @PathVariable("name") String name
  );
}
