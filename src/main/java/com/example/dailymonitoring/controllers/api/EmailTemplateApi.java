package com.example.dailymonitoring.controllers.api;

import com.example.dailymonitoring.models.EmailTemplateData;
import com.example.dailymonitoring.models.Error;
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

@Api(value = "EmailTemplates")
@Validated
public interface EmailTemplateApi {

  @ApiOperation(value = "Add email template", nickname = "addTemplate",
      response = EmailTemplateData.class, tags = { "EmailTemplates", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = EmailTemplateData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/templates",
      method = RequestMethod.POST,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<EmailTemplateData> addTemplate(
      @ApiParam(required = true) @RequestBody @Valid EmailTemplateData emailTemplateData
  );

  @ApiOperation(value = "Get email template by id", nickname = "getTemplateById",
      response = EmailTemplateData.class, tags = { "EmailTemplates", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = EmailTemplateData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/templates/{templateId}",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<EmailTemplateData> getTemplateById(
      @ApiParam(required = true) @Min(1) @PathVariable("templateId") Long templateId
  );

  @ApiOperation(value = "Get email templates", nickname = "getTemplates",
      response = EmailTemplateData.class, tags = { "EmailTemplates", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = EmailTemplateData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/templates",
      method = RequestMethod.GET,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<List<EmailTemplateData>> getTemplates();

  @ApiOperation(value = "Delete email template by id", nickname = "deleteTemplateById",
      response = EmailTemplateData.class, tags = { "EmailTemplates", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = EmailTemplateData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/templates/{templateId}",
      method = RequestMethod.DELETE,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<Void> deleteTemplate(
      @ApiParam(required = true) @Min(1) @PathVariable("templateId") Long templateId
  );

  @ApiOperation(value = "Update email template by id", nickname = "updateTemplateById",
      response = EmailTemplateData.class, tags = { "EmailTemplates", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = EmailTemplateData.class),
      @ApiResponse(code = 400, message = "Bad Request  ", response = Error.class),
      @ApiResponse(code = 403, message = "Forbidden  ", response = Error.class),
      @ApiResponse(code = 404, message = "Not Found  ", response = Error.class),
      @ApiResponse(code = 500, message = "Internal Server Error  ", response = Error.class),
      @ApiResponse(code = 503, message = "Service Unavailable  ", response = Error.class) })
  @RequestMapping(
      value = "/templates/{templateId}",
      method = RequestMethod.PUT,
      consumes = "application/json;charset=utf-8",
      produces = "application/json;charset=utf-8"
  )
  ResponseEntity<EmailTemplateData> updateTemplate(
      @ApiParam(required = true) @Min(1) @PathVariable("templateId") Long templateId,
      @ApiParam(required = true) @RequestBody @Valid EmailTemplateData emailTemplateData
  );
}
