package com.cinemazing.facade;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Coupon;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;

import java.util.List;

public class CustomerFacade extends ClientFacade {


    public CustomerFacade() {
    }

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsByEmailAndPassword(email, password)) {
            throw new CouponSystemException("Customer login failed, please try again.");
        }
        return true;
    }


    public void PurchaseCoupon(int customerID, int couponID) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CouponSystemException("Customer does not exist");
        }
        if (!couponsDAO.isCouponExists(couponID)) {
            throw new CouponSystemException("Coupon does not exist");
        }

        if (couponsDAO.isCouponExistsByCustomerID(customerID, couponID)) {
            throw new CouponSystemException("Coupon has already been purchased by this customer");
        }
        //TODO - DO IT ON couponFromDB
        if (couponsDAO.isCouponExpired(couponID)) {
            throw new CouponSystemException("Coupon has expired");
        }
        //TODO - DO IT ON couponFromDB
        if ((couponsDAO.getOneCoupon(couponID).getAmount() == 0)) {
            throw new CouponSystemException("Coupon is out of stock");
        }

        Coupon couponFromDB = couponsDAO.getOneCoupon(couponID);
        couponFromDB.setAmount(couponFromDB.getAmount() - 1);
        couponsDAO.updateCoupon(couponFromDB);
        couponsDAO.addCouponPurchase(customerID, couponID);
    }


    public List<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CouponSystemException("Customer does not exist");
        }
        return couponsDAO.getCustomerCoupons(customerID);
    }


    public List<Coupon> getCustomerCoupons(int customerID, Category category) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CouponSystemException("Customer does not exist");
        }
        return couponsDAO.getCustomerCouponsByCategory(customerID, category.getId());
    }


    public List<Coupon> getCustomerCoupons(int customerID, double maxPrice) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CouponSystemException("Customer does not exist");
        }
        return couponsDAO.getCustomerCouponsByMaxPrice(customerID, maxPrice);
    }


    public Customer getCustomerDetails(int customerID) {
        return customersDAO.getOneCustomer(customerID);
    }


}
