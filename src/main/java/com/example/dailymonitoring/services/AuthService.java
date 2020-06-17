package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.auth.AuthenticationProviderRequestData;
import com.example.dailymonitoring.models.auth.AuthenticationRequestData;
import com.example.dailymonitoring.models.auth.AuthenticationResponseData;

/**
 * @author Sirghi Mihail
 */
public interface AuthService {
  AuthenticationResponseData authenticate(AuthenticationRequestData authenticationRequestData);

  AuthenticationResponseData renewToken(AuthenticationResponseData authenticationResponseData);

  AuthenticationResponseData authenticateWithProvider(AuthenticationProviderRequestData data);
}
