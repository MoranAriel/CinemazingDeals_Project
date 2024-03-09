package com.cinemazing.facade;

import com.cinemazing.beans.Category;
import com.cinemazing.beans.Coupon;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;

import java.time.LocalDate;
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
            throw new CouponSystemException("This coupon has already been purchased by this customer");
        }

        Coupon couponFromDB = couponsDAO.getOneCoupon(couponID);

        if (couponFromDB.getAmount() == 0) {
            throw new CouponSystemException("Coupon is out of stock");
        }

        if (LocalDate.now().isAfter(couponFromDB.getEndDate())) {
            throw new CouponSystemException("Coupon has expired");
        }
            couponFromDB.setAmount(couponFromDB.getAmount() - 1);
            couponsDAO.updateCoupon(couponFromDB);
            couponsDAO.addCouponPurchase(customerID, couponID);
        }



        public List<Coupon> getCustomerCoupons ( int customerID) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("Customer does not exist");
            }
            return couponsDAO.getCustomerCoupons(customerID);
        }


        public List<Coupon> getCustomerCoupons ( int customerID, Category category) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("Customer does not exist");
            }
            return couponsDAO.getCustomerCouponsByCategory(customerID, category.getId());
        }


        public List<Coupon> getCustomerCoupons ( int customerID, double maxPrice) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("Customer does not exist");
            }
            return couponsDAO.getCustomerCouponsByMaxPrice(customerID, maxPrice);
        }


        public Customer getCustomerDetails ( int customerID) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("Customer does not exist");
            }
            Customer customer = customersDAO.getOneCustomer(customerID);
            customer.setCoupons(couponsDAO.getCustomerCoupons(customerID));
            return customer;
        }
    }



