package com.cinemazing.tests;

import com.cinemazing.beans.Company;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.AdminFacade;
import com.cinemazing.facade.CompanyFacade;
import com.cinemazing.login.ClientType;
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
    addCouponTest();
    System.out.println();
    updateCouponTest();
    System.out.println();
    deleteCouponTest();
    System.out.println();
    getCompanyCouponsTest();
    System.out.println();
    getCompanyDetailsTest();
  }

  public static void loginTest() {
    Company companyForLogin = new Company(1, "TestCompany", "TestCompany2@gmail.com", "12345",null);
    Company companyForLogin2 = new Company(0, "TestCompany222", "TestCompany222@gmail.com", "2222", null);

    try {
      System.out.println("Company login test (success):");
      companyFacade = (CompanyFacade) loginManager.login(companyForLogin.getEmail(), companyForLogin.getPassword(), ClientType.COMPANY);
      System.out.println("Login Successful. Welcome Company "+companyForLogin.getId()+"!"); //If login is successful, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Company login test (fail error with email or password):");
      companyFacade = (CompanyFacade) loginManager.login(companyForLogin2.getEmail(), companyForLogin2.getPassword(), ClientType.COMPANY);
      System.out.println("Login Successful. Welcome Company "+companyForLogin2.getId()+"!"); //If login is successful, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

  }

  public static void addCouponTest() {
  }

  public static void updateCouponTest() {
  }

  public static void deleteCouponTest() {
  }

  public static void getCompanyCouponsTest() {
  }

  public static void getCompanyDetailsTest() {
  }

}
