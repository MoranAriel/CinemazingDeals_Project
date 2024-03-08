package com.cinemazing.tests;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Company;
import com.cinemazing.beans.Coupon;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.AdminFacade;
import com.cinemazing.facade.CompanyFacade;
import com.cinemazing.login.ClientType;
import com.cinemazing.login.LoginManager;

import java.time.LocalDate;
import java.util.List;

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
    Coupon couponToAdd = new Coupon(0, Category.DVD_AND_BLUERAY, "1+1","get 1+1 ha ha",
            LocalDate.now(),LocalDate.of(2024,12,12),10,30.0,"imagelink");
    Coupon couponToAdd2 = new Coupon(0, Category.STREAMING, "Free Month","get first month free",
            LocalDate.now(),LocalDate.of(2024,12,12),10,30.0,"imagelink");
    Coupon couponToAdd3 = new Coupon(0, Category.POST_PRODUCTION, "cant think of a name","cant think of a description",
            LocalDate.now(),LocalDate.of(2024,12,12),10,30.0,"imagelink");
    Coupon couponToAdd4 = new Coupon(0, Category.PRODUCTION, "still cant think of a name","still cant think of a description",
            LocalDate.now(),LocalDate.of(2024,12,12),10,40.0,"imagelink");

    try {
      System.out.println("Add Coupon test (success)");
      companyFacade.addCoupon(1,couponToAdd);
      System.out.println("Coupon added successfully: " + couponToAdd);//If coupon added successfully, you'll see this message.
      companyFacade.addCoupon(3,couponToAdd2);
      System.out.println("Coupon added successfully: " + couponToAdd2);//If coupon added successfully, you'll see this message.
      companyFacade.addCoupon(1,couponToAdd3);
      System.out.println("Coupon added successfully: " + couponToAdd3);//If coupon added successfully, you'll see this message.
      companyFacade.addCoupon(3,couponToAdd4);
      System.out.println("Coupon added successfully: " + couponToAdd4);//If coupon added successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Add Coupon test (fail, coupon title already exists)");
      companyFacade.addCoupon(1,couponToAdd);
      System.out.println("Coupon added successfully: " + couponToAdd);//If coupon added successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Add Coupon test (fail, company doesn't exist)");
      companyFacade.addCoupon(8,couponToAdd);
      System.out.println("Coupon added successfully: " + couponToAdd);//If coupon added successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void updateCouponTest() {
    Coupon couponToUpdate = new Coupon(1,1, Category.DVD_AND_BLUERAY, "1+1","get 1+1 ha ha",
            LocalDate.now(),LocalDate.of(2024,12,12),10,30.0,"imagelink");//couponToAdd from Add Test
    couponToUpdate.setCategory(Category.MOVIE_THEATERS);
    couponToUpdate.setTitle("Get free popcorn");
    couponToUpdate.setDescription("Get a free popcorn when purchasing a ticket");

    try {
      System.out.println("Update Coupon test (success)");
      companyFacade.updateCoupon(1,couponToUpdate);
      System.out.println("Coupon Updated Successfully: "+ couponToUpdate);//If coupon updated successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      couponToUpdate.setId(100);
      System.out.println("Update Coupon test (fail, coupon doesn't exist)");
      companyFacade.updateCoupon(1,couponToUpdate);
      System.out.println("Coupon Updated Successfully: "+ couponToUpdate);//If coupon updated successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      couponToUpdate.setId(1);
      System.out.println("Update Coupon test (fail, company doesn't exist)");
      companyFacade.updateCoupon(8,couponToUpdate);
      System.out.println("Coupon Updated Successfully: "+ couponToUpdate);//If coupon updated successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Update Coupon test (fail, coupon doesn't belong to company)");
      companyFacade.updateCoupon(3,couponToUpdate);
      System.out.println("Coupon Updated Successfully: "+ couponToUpdate);//If coupon updated successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      couponToUpdate.setTitle("can't think of a name");
      System.out.println("Update Coupon test (fail, coupon title already exists)");
      companyFacade.updateCoupon(1,couponToUpdate);
      System.out.println("Coupon Updated Successfully: "+ couponToUpdate);//If coupon updated successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void deleteCouponTest() {

    try {
      System.out.println("Delete Coupon test (success)");
      companyFacade.deleteCoupon(1,1);
      System.out.println("Coupon "+1+" Deleted Successfully.");//If coupon deleted successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Delete Coupon test (fail, coupon doesn't exist)");
      companyFacade.deleteCoupon(1,6);
      System.out.println("Coupon "+6+" Deleted Successfully.");//If coupon deleted successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Delete Coupon test (fail, company doesn't exist)");
      companyFacade.deleteCoupon(77,3);
      System.out.println("Coupon "+6+" Deleted Successfully.");//If coupon deleted successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Delete Coupon test (fail, coupon doesn't belong to this company)");
      companyFacade.deleteCoupon(3,3);
      System.out.println("Coupon "+6+" Deleted Successfully.");//If coupon deleted successfully, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void getCompanyCouponsTest() {

    try {
      System.out.println("Get Company Coupons Test (success)");
      List<Coupon> companyCoupons = companyFacade.getCompanyCoupons(3);
      System.out.println("Company Coupons: " + companyCoupons);//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Get Company Coupons by Category Test (success)");
      List<Coupon> companyCoupons = companyFacade.getCompanyCoupons(3, Category.PRODUCTION);
      System.out.println("Company Coupons by Category: " + companyCoupons);//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Get Company Coupons by Max Price Test (success)");
      List<Coupon> companyCoupons = companyFacade.getCompanyCoupons(3, 35);
      System.out.println("Company Coupons by Max Price: " + companyCoupons);//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Get Company Coupons Test (fail, company doesn't exist)");
      List<Coupon> companyCoupons = companyFacade.getCompanyCoupons(4);
      System.out.println("Company Coupons: " + companyCoupons);//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

  }

  public static void getCompanyDetailsTest() {
    try {
      System.out.println("Get Company Details test (success)");
      System.out.println(companyFacade.getCompanyDetails(3));//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Get Company Details test (fail, company doesn't exist)");
      System.out.println(companyFacade.getCompanyDetails(2));//If successful, you'll see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

}
