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
            throw new CouponSystemException("\u001b[31mCustomer login failed, please try again.\u001b[0m");
        }
        return true;
    }


    public void PurchaseCoupon(int customerID, int couponID) throws CouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerID)) {
            throw new CouponSystemException("\u001b[31mCustomer does not exist\u001b[0m");
        }
        if (!couponsDAO.isCouponExists(couponID)) {
            throw new CouponSystemException("\u001b[31mCoupon does not exist\u001b[0m");
        }

        if (couponsDAO.isCouponExistsByCustomerID(customerID, couponID)) {
            throw new CouponSystemException("\u001b[31mThis coupon has already been purchased by this customer\u001b[0m");
        }

        Coupon couponFromDB = couponsDAO.getOneCoupon(couponID);

        if (couponFromDB.getAmount() == 0) {
            throw new CouponSystemException("\u001b[31mCoupon is out of stock\u001b[0m");
        }

        if (LocalDate.now().isAfter(couponFromDB.getEndDate())) {
            throw new CouponSystemException("\u001b[31mCoupon has expired\u001b[0m");
        }
            couponFromDB.setAmount(couponFromDB.getAmount() - 1);
            couponsDAO.updateCoupon(couponFromDB);
            couponsDAO.addCouponPurchase(customerID, couponID);
        }



        public List<Coupon> getCustomerCoupons ( int customerID) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("\u001b[31mCustomer does not exist\u001b[0m");
            }
            return couponsDAO.getCustomerCoupons(customerID);
        }


        public List<Coupon> getCustomerCoupons ( int customerID, Category category) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("\u001b[31mCustomer does not exist\u001b[0m");
            }
            return couponsDAO.getCustomerCouponsByCategory(customerID, category.getId());
        }


        public List<Coupon> getCustomerCoupons ( int customerID, double maxPrice) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("\u001b[31mCustomer does not exist\u001b[0m");
            }
            return couponsDAO.getCustomerCouponsByMaxPrice(customerID, maxPrice);
        }


        public Customer getCustomerDetails ( int customerID) throws CouponSystemException {
            if (!customersDAO.isCustomerExistsById(customerID)) {
                throw new CouponSystemException("\u001b[31mCustomer does not exist\u001b[0m");
            }
            Customer customer = customersDAO.getOneCustomer(customerID);
            customer.setCoupons(couponsDAO.getCustomerCoupons(customerID));
            return customer;
        }
    }



