package com.cinemazing.tests;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Coupon;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.CompanyFacade;
import com.cinemazing.facade.CustomerFacade;
import com.cinemazing.login.ClientType;
import com.cinemazing.login.LoginManager;

import java.time.LocalDate;

public class CustomerTest {

    private static CustomerFacade customerFacade;

    private static LoginManager loginManager = LoginManager.getInstance();

    private static final CustomerTest instance = new CustomerTest();

    public static CustomerTest getInstance() {
        return instance;
    }

    private CustomerTest() {
    }

    public static void runAllCustomerTest() {
        loginTest();
        System.out.println();
        purchaseCouponTest();
        System.out.println();
        getCustomerCouponsTest();
        System.out.println();
        getCustomerCouponByCategoryTest();
        System.out.println();
        getCustomerCouponsByMaxPriceTest();
        System.out.println();
        getCustomerDetails();
    }

    public static void loginTest() {
        Customer customerForLogin = new Customer(1, "Doron", "Berger", "doronbrgr222@gmail.com", "MillieIsTheBest222");
        Customer customerForLogin2 = new Customer(2, "Moran", "Ariel", "moranariel@gmail.com", "ArbelIsTheBest");

        try {
            System.out.println("Customer Login test (success)");
            customerFacade = (CustomerFacade) loginManager.login(customerForLogin.getEmail(), customerForLogin.getPassword(), ClientType.CUSTOMER);
            System.out.println("Login Successful, Welcome Customer " + customerForLogin.getId());//If login successful, you'll see this message.
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Customer Login test (fail, customer doesn't exist)");
            customerFacade = (CustomerFacade) loginManager.login(customerForLogin2.getEmail(), customerForLogin2.getPassword(), ClientType.CUSTOMER);
            System.out.println("Login Successful, Welcome Customer " + customerForLogin2.getId());//If login successful, you'll see this message.
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void purchaseCouponTest() {

        try {
            customerFacade.PurchaseCoupon(1,4);
            customerFacade.PurchaseCoupon(1,3);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getCustomerCouponsTest() {
        try {
            System.out.println("Get All Purchased Coupon Successfully:\n" + customerFacade.getCustomerCoupons(1));
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getCustomerCouponByCategoryTest() {
        try {
            System.out.println("Get All Purchased Coupon Successfully:\n" + customerFacade.getCustomerCoupons(1, Category.PRODUCTION));
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getCustomerCouponsByMaxPriceTest() {
        try {
            System.out.println("Get All Purchased Coupon Successfully:\n" + customerFacade.getCustomerCoupons(1, 30));
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getCustomerDetails() {
        System.out.println("Customer Details:");
        try {
            System.out.println(customerFacade.getCustomerDetails(1));
        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        }
        ;
    }

}
