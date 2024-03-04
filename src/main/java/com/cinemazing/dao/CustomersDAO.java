package com.cinemazing.dao;

import java.util.List;

import com.cinemazing.beans.Customer;

public interface CustomersDAO {

    public boolean isCustomerExistsByEmailAndPassword(String email, String password);

    public boolean isCustomerExistsByEmail(String email);

    public boolean isCustomerExistsByEmailAndNotId(String email, int id);

    public boolean isCustomerExistsById(int id);

    public void addCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public void deleteCustomer(int customerID);

    public List<Customer> getAllCustomers();

    public Customer getOneCustomer(int customerID);


}
