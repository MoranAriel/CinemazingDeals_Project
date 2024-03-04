package com.cinemazing.tests;

import com.cinemazing.facade.CompanyFacade;
import com.cinemazing.login.LoginManager;

public class CompanyTest {
  private static CompanyFacade companyFacade;

  private static LoginManager loginManager = LoginManager.getInstance();

  private static final CompanyTest instance = new CompanyTest();

  public static CompanyTest getInstance() {
    return instance;
  }

  private CompanyTest() {
  }

  public static void runAllCompanyTest() {
    loginTest();
    System.out.println();
  }

  // todo-  add code here
  public static void loginTest() {

  }


}
