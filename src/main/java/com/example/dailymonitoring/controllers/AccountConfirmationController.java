package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.AccountConfirmationApi;
import com.example.dailymonitoring.services.AccountConfirmationService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountConfirmationController implements AccountConfirmationApi {

  private final AccountConfirmationService accountConfirmationService;

  public AccountConfirmationController(AccountConfirmationService accountConfirmationService) {
    this.accountConfirmationService = accountConfirmationService;
  }

  @Override
  public void activateAccount(String token, HttpServletResponse httpServletResponse) {
    String result = accountConfirmationService.confirmRegistration(token);

    // TODO: implement redirect logic when FE is ready
    System.out.println(result);
    httpServletResponse.setHeader("Location", "http://localhost:4200/login?activated=true");
    httpServletResponse.setStatus(302);
  }
}
