package com.cinemazing.job;

import java.util.concurrent.TimeUnit;

import com.cinemazing.dao.CouponsDAO;
import com.cinemazing.dao.CouponsDBDAO;

public class CouponExcperationJob extends Thread {

  private CouponsDAO couponsDAO;

  public CouponExcperationJob() {
    this.couponsDAO = new CouponsDBDAO();
    // this.setDaemon(true);
  }

  @Override
  public void run() {
    while (true) {

      couponsDAO.deleteExpiredCoupons();
      
      try {
        TimeUnit.HOURS.sleep(24);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}