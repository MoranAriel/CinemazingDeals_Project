package com.cinemazing.dao;

import java.util.List;

import com.cinemazing.beans.Coupon;

public interface CouponsDAO {
  public void addCoupon(Coupon coupon);

  public void updateCoupon(Coupon coupon);

  public void deleteCoupon(int couponID);


  public List<Coupon> getCompanyCoupons(int companyID);

  public List<Coupon> getCompanyCouponsByCategory(int companyID, int categoryID);

  public List<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice);

  public List<Coupon> getCustomerCoupons(int companyID);

  public List<Coupon> getCustomerCouponsByCategory(int companyID, int categoryID);

  public List<Coupon> getCustomerCouponsByMaxPrice(int companyID, double maxPrice);

  public boolean isCouponExists(int couponID);

  public boolean isCouponExistsByCustomerID(int customerID, int couponID);


  public boolean isCouponExistsByTitleAndCompanyId(String title, int companyID);

  public Coupon getOneCoupon(int couponID);



  public void deleteCouponPurchaseByCouponId(int couponID);

  public void deleteCouponPurchaseByCustomerId(int customerID);

  public boolean isCouponExpired(int couponID);
  public void deleteExpiredCoupons();


  public List<Coupon> getAllCoupons();
  public void addCouponPurchase(int customerID, int couponID);

  public void deleteCouponPurchase(int customerID, int couponID);


//  public Map<Integer, ArrayList<Integer>> getCouponPurchaseHistory(List<Customer> customers);



}
