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
      throw new CouponSystemException("\u001b[31mCompany login failed, please try again.\u001b[0m");
    }
    return true;
  }

  public void addCoupon(int companyID, Coupon coupon) throws CouponSystemException {
    if (couponsDAO.isCouponExistsByTitleAndCompanyId(coupon.getTitle(), companyID)) {
      throw new CouponSystemException("\u001b[31mCoupon title already exists\u001b[0m");
    }
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    coupon.setCompanyID(companyID);
    couponsDAO.addCoupon(coupon);
  }

  public void updateCoupon(int companyID, Coupon coupon) throws CouponSystemException {
    if (!couponsDAO.isCouponExists(coupon.getId())) {
      throw new CouponSystemException("\u001b[31mCoupon does not exist\u001b[0m");
    }

    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }

    Coupon couponFromDB = couponsDAO.getOneCoupon(coupon.getId());
    if (couponFromDB.getCompanyID() != companyID) {
      throw new CouponSystemException("\u001b[31mCoupon does not belong to this company\u001b[0m");
    }

    if (!(couponFromDB.getTitle().equals(coupon.getTitle()))) {
      if (couponsDAO.isCouponExistsByTitleAndCompanyId(coupon.getTitle(), companyID)) {
        throw new CouponSystemException("\u001b[31mCoupon title already exists\u001b[0m");
      }
    }
    couponsDAO.updateCoupon(coupon);
  }

  public void deleteCoupon(int companyID, int couponID) throws CouponSystemException {
    if (!couponsDAO.isCouponExists(couponID)) {
      throw new CouponSystemException("\u001b[31mCoupon does not exist\u001b[0m");
    }
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    if (couponsDAO.getOneCoupon(couponID).getCompanyID() != companyID) {
      throw new CouponSystemException("\u001b[31mCoupon does not belong to this company\u001b[0m");
    }
    couponsDAO.deleteCouponPurchaseByCouponId(couponID);
    couponsDAO.deleteCoupon(couponID);
  }

  public List<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    return couponsDAO.getCompanyCoupons(companyID);
  }

  public List<Coupon> getCompanyCoupons(int companyID, Category category) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    return couponsDAO.getCompanyCouponsByCategory(companyID, category.getId());

  }

  public List<Coupon> getCompanyCoupons(int companyID, double maxPrice) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    return couponsDAO.getCompanyCouponsByMaxPrice(companyID, maxPrice);
  }

  public Company getCompanyDetails(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("\u001b[31mCompany does not exist\u001b[0m");
    }
    Company company = companiesDAO.getOneCompany(companyID);
    company.setCoupons(getCompanyCoupons(companyID));
    return company;
  }
}
