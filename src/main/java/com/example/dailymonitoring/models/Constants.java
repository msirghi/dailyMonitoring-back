package com.example.dailymonitoring.models;

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
}
