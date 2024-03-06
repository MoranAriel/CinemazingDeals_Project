package com.cinemazing.facade;

import java.util.List;

import com.cinemazing.beans.Company;
import com.cinemazing.beans.Coupon;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;

public class AdminFacade extends ClientFacade {

  public AdminFacade() {

  }

  @Override
  public boolean login(String email, String password) throws CouponSystemException {
    if (!(email.equalsIgnoreCase("admin@admin.com") && password.equals("admin"))) {
      throw new CouponSystemException("Login failed because email or password is wrong");
    }
    return true;
  }

  public void addCompany(Company company) throws CouponSystemException {
    if (companiesDAO.isCompanyExistsByName(company.getName())) {
      throw new CouponSystemException("Company name already exists");
    }

    if (companiesDAO.isCompanyExistsByEmail(company.getEmail())) {
      throw new CouponSystemException("Company email already exists");
    }
    companiesDAO.addCompany(company);
  }

  public void updateCompany(Company company) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(company.getId())) {
      throw new CouponSystemException("Company does not exist");
    }

    Company companyFromDB = companiesDAO.getOneCompany(company.getId());

    if (!companyFromDB.getName().equals(company.getName())) {
      throw new CouponSystemException("Company name cannot be changed");
    }

    companyFromDB.setEmail(company.getEmail());
    companyFromDB.setPassword(company.getPassword());

    companiesDAO.updateCompany(companyFromDB);
  }

  public void deleteCompany(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    List<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);
    for (Coupon coupon : coupons) {
      couponsDAO.deleteCouponPurchaseByCouponId(coupon.getId());
      couponsDAO.deleteCoupon(coupon.getId());
    }
    companiesDAO.deleteCompany(companyID);
  }

  public List<Company> getAllCompanies() {
    return companiesDAO.getAllCompanies();
  }

  public Company getOneCompany(int companyID) throws CouponSystemException {
    if (!companiesDAO.isCompanyExistsById(companyID)) {
      throw new CouponSystemException("Company does not exist");
    }
    return companiesDAO.getOneCompany(companyID);
  }

  public void addCustomer(Customer customer) throws CouponSystemException {

    if (customersDAO.isCustomerExistsByEmail(customer.getEmail())) {
      throw new CouponSystemException("Customer email already exists");
    }

    customersDAO.addCustomer(customer);
  }

  public void updateCustomer(Customer customer) throws CouponSystemException {
    if (!customersDAO.isCustomerExistsById(customer.getId())) {
      throw new CouponSystemException("Customer does not exist");
    }
    if (customersDAO.isCustomerExistsByEmailAndNotId(customer.getEmail(), customer.getId())) {
      throw new CouponSystemException("Customer email already exists");
    }
    customersDAO.updateCustomer(customer);
  }

  public void deleteCustomer(int customerID) throws CouponSystemException {
    if (!customersDAO.isCustomerExistsById(customerID)) {
      throw new CouponSystemException("Customer does not exist");
    }
    couponsDAO.deleteCouponPurchaseByCustomerId(customerID);
    customersDAO.deleteCustomer(customerID);
  }

  public List<Customer> getAllCustomers() {
    return customersDAO.getAllCustomers();
  }

  public Customer getOneCustomer(int customerID) throws CouponSystemException {
    if (!customersDAO.isCustomerExistsById(customerID)) {
      throw new CouponSystemException("Customer does not exist");
    }
    return customersDAO.getOneCustomer(customerID);
  }
}
