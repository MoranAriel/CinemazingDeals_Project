package com.cinemazing.tests;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Coupon;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.CustomerFacade;
import com.cinemazing.login.LoginManager;

import java.time.LocalDate;

public class CustomerTest {

    private static CustomerFacade customerFacade = new CustomerFacade();

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
        getCustomerDetails();
    }

    public static void loginTest() {
        Customer customerForLogin = new Customer(1, "Doron", "Berger", "doronbrgr222@gmail.com", "MillieIsTheBest222");
        Customer customerForLogin2 = new Customer(2, "Moran", "Ariel", "moranariel@gmail.com", "ArbelIsTheBest");

        try {
            System.out.println("Customer Login test (success)");
            customerFacade.login(customerForLogin.getEmail(), customerForLogin.getPassword());
            System.out.println("Login Successful, Welcome Customer " + customerForLogin.getId());//If login successful, you'll see this message.
        } catch (CouponSystemException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("Customer Login test (fail, customer doesn't exist)");
            customerFacade.login(customerForLogin2.getEmail(), customerForLogin2.getPassword());
            System.out.println("Login Successful, Welcome Customer " + customerForLogin2.getId());//If login successful, you'll see this message.
        } catch (CouponSystemException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void purchaseCouponTest() {

        try {
            customerFacade.PurchaseCoupon(1,4);
            System.out.println("Coupon Purchased Successfully: " + customerFacade.getCustomerCoupons(1));
        } catch (CouponSystemException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getCustomerCouponsTest() {

    }

    public static void getCustomerDetails() {

    }

}
