package com.cinemazing.tests;

import com.cinemazing.database.DBManager;
import com.cinemazing.job.CouponExcperationJob;

public class AllTest {

    private static final AllTest instance = new AllTest();

    private AllTest() {    }

    public static AllTest getInstance() {
        return instance;
    }

    public static void runAllAdminTest() {
        DBManager.createDataBase();
        CouponExcperationJob couponExcperationJob = new CouponExcperationJob();
        couponExcperationJob.start();
        AdminTest.runAllAdminTest();
    }
}
