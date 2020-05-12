package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.AccountConfirmationApi;
import com.example.dailymonitoring.services.AccountConfirmationService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountConfirmationController implements AccountConfirmationApi {

  @Autowired
  private AccountConfirmationService accountConfirmationService;

  @Override
  public void activateAccount(String token, HttpServletResponse httpServletResponse) {
    String result = accountConfirmationService.confirmRegistration(token);

    // TODO: implement redirect logic when FE is ready
    System.out.println(result);
    httpServletResponse.setHeader("Location", "https://www.google.com/");
    httpServletResponse.setStatus(302);
  }
}
