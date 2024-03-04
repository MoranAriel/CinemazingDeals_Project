package com.cinemazing.facade;

import com.cinemazing.dao.CompaniesDBDAO;
import com.cinemazing.dao.CompaniesDAO;
import com.cinemazing.dao.CouponsDAO;
import com.cinemazing.dao.CouponsDBDAO;
import com.cinemazing.dao.CustomersDAO;
import com.cinemazing.dao.CustomersDBDAO;
import com.cinemazing.exceptions.CouponSystemException;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    ClientFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customersDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }


    public abstract boolean login(String email, String password) throws CouponSystemException;



}
