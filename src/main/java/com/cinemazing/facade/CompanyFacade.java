package com.cinemazing.facade;

import java.util.List;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Company;
import com.cinemazing.beans.Coupon;
import com.cinemazing.exceptions.CouponSystemException;

public class CompanyFacade extends ClientFacade {

  public CompanyFacade() {
  }

  @Override
  public boolean login(String email, String password) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsByEmailAndPassword(email, password)) {
      throw new CouponSystemException("Company login failed, please try again.");
    }
    return true;
  }

  public void addCoupon(int companyID, Coupon coupon) throws CouponSystemException {
    if (couponsDAO.isCouponExistsByTitleAndCompanyId(coupon.getTitle(), companyID)) {
      throw new CouponSystemException("Coupon title already exists");
    }
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    coupon.setCompanyID(companyID);
    couponsDAO.addCoupon(coupon);
  }

  public void updateCoupon(int companyID, Coupon coupon) throws CouponSystemException {
    if (!couponsDAO.isCouponExists(coupon.getId())) {
      throw new CouponSystemException("Coupon does not exist");
    }

    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }

    Coupon couponFromDB = couponsDAO.getOneCoupon(coupon.getId());
    if (couponFromDB.getCompanyID() != companyID) {
      throw new CouponSystemException("Coupon does not belong to this company");
    }

    if (!(couponFromDB.getTitle().equals(coupon.getTitle()))) {
      if (couponsDAO.isCouponExistsByTitleAndCompanyId(coupon.getTitle(), companyID)) {
        throw new CouponSystemException("Coupon title already exists");
      }
    }
    couponsDAO.updateCoupon(coupon);
  }

  public void deleteCoupon(int companyID, int couponID) throws CouponSystemException {
    if (!couponsDAO.isCouponExists(couponID)) {
      throw new CouponSystemException("Coupon does not exist");
    }
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    if (couponsDAO.getOneCoupon(couponID).getCompanyID() != companyID) {
      throw new CouponSystemException("Coupon does not belong to this company");
    }
    couponsDAO.deleteCouponPurchaseByCouponId(couponID);
    couponsDAO.deleteCoupon(couponID);
  }

  public List<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    return couponsDAO.getCompanyCoupons(companyID);
  }

  public List<Coupon> getCompanyCoupons(int companyID, Category category) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    return couponsDAO.getCompanyCouponsByCategory(companyID, category.getId());

  }

  public List<Coupon> getCompanyCoupons(int companyID, double maxPrice) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    return couponsDAO.getCompanyCouponsByMaxPrice(companyID, maxPrice);
  }

  public Company getCompanyDetails(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    Company company = companiesDAO.getOneCompany(companyID);
    company.setCoupons(getCompanyCoupons(companyID));
    return company;
  }
}
