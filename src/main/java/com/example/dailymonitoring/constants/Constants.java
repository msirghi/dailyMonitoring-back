package com.example.dailymonitoring.constants;

import com.example.dailymonitoring.configs.utils.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class Constants {

  public static final String USERNAME_ERROR = "Username can contain only "
      + "alphabetic , numeric , . and _ characters.";
  public static final String PASSWORD_ERROR = "Minimum eight characters,"
      + " at least one letter and one number.";
  public static final String FULLNAME_ERROR = "Letters only , "
      + "one space between words , Max : First name , last name , patronymic , Min : First name.";
  public static final String EMAIL_ERROR = "Email must be"
      + " like example@expample.com";

  public static final String USERNAME_REGEX = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
  public static final String PASSWORD_REGEX = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
  public static final String FULLNAME_REGEX = "^([a-zA-Z]+|[a-zA-Z]+\\s{1}[a-zA-Z]{1,}|[a-zA-Z]+"
      + "\\s{1}[a-zA-Z]{3,}\\s{1}[a-zA-Z]{1,})$";
  public static final String EMAIL_REGEX = ".+@.+\\..+";

  public static final String USER_EMAIL_NOT_FOUND = "User with such email was not found in our system.";
  public static final String PROJECT_USER_ADD_BODY = "Check daily-monitoring.net for more info!";
  public static final String PROJECT_USER_SUBJECT = "You were invited to the %s project";
  public static final String EMAIL_SENT_SUCCESS = "Email sent successfully";
  public static final String NO_SUCH_PROJECT = "This project does not exist";
  public static final String PROJECT_USER_DELETED_SUBJECT = "You were deleted from the %s "
      + "project";
  public static final String PROJECT_USER_DELETED_BODY = "You were deleted from the %s longer "
      + "have access.";
  public static final String PROJECT_USER_ALREADY_EXISTS = "User is already in the project";
  public static final String PROJECT_NOT_FOUND = "Project not found";

  public static final String TOKEN_INVALID = "Token is invalid";
  public static final String TOKEN_EXPIRED = "Token is expired";
  public static final String TOKEN_VALID = "Token is valid";

  public static final String EMAIL_CONFIRMATION_SUBJECT = "Registration Confirmation";
  public static final String NO_USER_WITH_SUCH_PROJECT = "No user with such project";
  public static final String NO_USER_FOUND = "No user fount with this id";
  public static final String ASSIGNED_TO_NOT_SPECIFIED = "Task should be assigned to someone";
  public static final String PROJECT_ID_NOT_EQUAL = "Project ids must not be equal";

  public static Long getCurrentUserId() {
    try {
      return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal()).getId();
    } catch (NullPointerException | ClassCastException e) {
      return 0L;
    }
  }

}
