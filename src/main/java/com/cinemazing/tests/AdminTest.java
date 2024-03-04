package com.cinemazing.tests;

import com.cinemazing.beans.Company;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.AdminFacade;
import com.cinemazing.login.ClientType;
import com.cinemazing.login.LoginManager;

public class AdminTest {

  private static AdminFacade adminFacade;
  private static LoginManager loginManager = LoginManager.getInstance();
  private static final AdminTest instance = new AdminTest();

  public static AdminTest getInstance() {
    return instance;
  }

  private AdminTest() {
  }

  public static void runAllAdminTest() {
    loginTest();
    System.out.println();
    addCompanyTest();
    System.out.println();
    updateCompanyTest();
    System.out.println();
    deleteCompanyTest();
    System.out.println();
  }

  public static void loginTest() {
    try {
      System.out.println("Admin login test (success):");
      adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Admin login test (fail email is wrong):");
      adminFacade = (AdminFacade) loginManager.login("dmin@admin.com", "admin", ClientType.ADMINISTRATOR);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Admin login test (fail password is wrong):");
      adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin1234", ClientType.ADMINISTRATOR);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void addCompanyTest() {
    Company companyToAdd = new Company(0, "TestCompany", "TestCompany@gmail.com", "1234", null);

    Company companyToAdd2 = new Company(0, "TestCompany222", "TestCompany222@gmail.com", "2222", null);

    try {
      System.out.println("Add company test (success):");
      adminFacade.addCompany(companyToAdd);
      adminFacade.addCompany(companyToAdd2);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Add company test (fail company name already exists):");
      adminFacade.addCompany(companyToAdd);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      companyToAdd.setName("newTestCompanyName");
      System.out.println("Add company test (fail company email already exists):");
      adminFacade.addCompany(companyToAdd);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void updateCompanyTest() {
    Company companyToUpdate= new Company(1, "TestCompany", "TestCompany2@gmail.com", "12345", null);
    try {
      System.out.println("Update company test (success):");
      adminFacade.updateCompany(companyToUpdate);
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void deleteCompanyTest(){
      try {
        System.err.println("Delete company test (success):");
        adminFacade.deleteCompany(2);
      } catch (CouponSystemException e) {
        System.err.println(e.getMessage());
      }
  }
}
