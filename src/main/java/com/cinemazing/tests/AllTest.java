package com.cinemazing.tests;

import com.cinemazing.database.ConnectionPool;
import com.cinemazing.database.DBManager;
import com.cinemazing.job.CouponExcperationJob;

public class AllTest {

    private static final AllTest instance = new AllTest();

    private AllTest() {    }

    public static AllTest getInstance() {
        return instance;
    }

    public static void runAllTests() {
        DBManager.createDataBase();
        CouponExcperationJob couponExcperationJob = new CouponExcperationJob();
        couponExcperationJob.start();
        AdminTest.runAllAdminTest();
        System.out.println();
        CompanyTest.runAllCompanyTest();
        System.out.println();
        CustomerTest.runAllCustomerTest();
        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
