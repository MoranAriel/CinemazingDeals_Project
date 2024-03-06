package com.cinemazing.tests;

import com.cinemazing.beans.Company;
import com.cinemazing.beans.Customer;
import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.AdminFacade;
import com.cinemazing.login.ClientType;
import com.cinemazing.login.LoginManager;

public class AdminTest {

  private static AdminFacade adminFacade;
  private static LoginManager loginManager = LoginManager.getInstance();
  private static final AdminTest instance = new AdminTest();

  public static AdminTest getInstance() {
    return instance;
  }

  private AdminTest() {
  }

  public static void runAllAdminTest() {
    loginTest();
    System.out.println();
    addCompanyTest();
    System.out.println();
    updateCompanyTest();
    System.out.println();
    deleteCompanyTest();
    System.out.println();
    getAllCompaniesTest();
    System.out.println();
    getOneCompanyTest();
    System.out.println();
    addCustomerTest();
    System.out.println();
    updateCustomerTest();
    System.out.println();
    deleteCustomerTest();
    System.out.println();
    getAllCustomersTest();
    System.out.println();
    getOneCustomerTest();
  }




  public static void loginTest() {
    try {
      System.out.println("Admin login test (success):");
      adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
      System.out.println("Login Successful. Welcome Admin!"); //If login is successful, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Admin login test (fail email is wrong):");
      adminFacade = (AdminFacade) loginManager.login("dmin@admin.com", "admin", ClientType.ADMINISTRATOR);
      System.out.println("Login Successful. Welcome Admin!"); //If login is successful, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Admin login test (fail password is wrong):");
      adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin1234", ClientType.ADMINISTRATOR);
      System.out.println("Login Successful. Welcome Admin!"); //If login is successful, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void addCompanyTest() {
    Company companyToAdd = new Company(0, "TestCompany", "TestCompany@gmail.com", "1234", null);

    Company companyToAdd2 = new Company(0, "TestCompany222", "TestCompany222@gmail.com", "2222", null);

    Company companyToAdd3 = new Company(0, "TestCompany333", "TestCompany333@gmail.com", "3333", null);

    try {
      System.out.println("Add company test (success):");
      adminFacade.addCompany(companyToAdd);
      System.out.println("Company Added Successfully: " + companyToAdd);//If company added successfully, you will see this message.
      adminFacade.addCompany(companyToAdd2);
      System.out.println("Company Added Successfully: " + companyToAdd2);//If company added successfully, you will see this message.
      adminFacade.addCompany(companyToAdd3);
      System.out.println("Company Added Successfully: " + companyToAdd3);//If company added successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Add company test (fail company name already exists):");
      adminFacade.addCompany(companyToAdd);
      System.out.println("Company Added Successfully: " + companyToAdd);//If company added successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      companyToAdd.setName("newTestCompanyName");
      System.out.println("Add company test (fail company email already exists):");
      adminFacade.addCompany(companyToAdd);
      System.out.println("Company Added Successfully: " + companyToAdd);//If company added successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void updateCompanyTest() {
    Company companyToUpdate= new Company(1, "TestCompany", "TestCompany2@gmail.com", "12345",null);
    Company companyToUpdate2= new Company(7, "TestCompany", "TestCompany2@gmail.com", "12345", null);

    try {
      System.out.println("Update company test (success):");
      adminFacade.updateCompany(companyToUpdate);
      System.out.println("Company Updated Successfully: " + companyToUpdate);//If company updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Update company test (fail company doesn't exist):");
      adminFacade.updateCompany(companyToUpdate2);
      System.out.println("Company Updated Successfully: " + companyToUpdate2);//If company updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      companyToUpdate.setName("TestCompany2000");
      System.out.println("Update company test (fail company name cannot be changed):");
      adminFacade.updateCompany(companyToUpdate);
      System.out.println("Company Updated Successfully: " + companyToUpdate);//If company updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void deleteCompanyTest(){
      try {
        System.out.println("Delete company test (success):");
        adminFacade.deleteCompany(2);
        System.out.println("Company Deleted Successfully");//If company deleted successfully, you will see this message.
      } catch (CouponSystemException e) {
        System.err.println(e.getMessage());
      }

    try {
      System.out.println("Delete company test (fail company does not exist):");
      adminFacade.deleteCompany(7);
      System.out.println("Company Deleted Successfully");//If company deleted successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void getAllCompaniesTest() {
    System.out.println("Get All Companies test (success)");
    System.out.println(adminFacade.getAllCompanies());
  }

  public static void getOneCompanyTest() {
    try{
      System.out.println("Get One Company test (success)");
      Company company = adminFacade.getOneCompany(1);
      System.out.println("Here is your Company: " + company);//If company got successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try{
      System.out.println("Get One Company test (fail company doesn't exist)");
      Company company2 = adminFacade.getOneCompany(8);
      System.out.println("Here is your Company: " + company2);//If company got successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void addCustomerTest() {
    Customer customerToAdd = new Customer(0, "Doron", "Berger", "doronbrgr@gmail.com", "MillieIsTheBest");

    Customer customerToAdd2 = new Customer(0, "Moran", "Ariel", "moranariel@gmail.com", "ArbelIsTheBest");

    Customer customerToAdd3 = new Customer(0, "Max", "Gunn", "maxgunn@gmail.com", "YourKidIsTheBest");

    try {
      System.out.println("Add customer test (success):");
      adminFacade.addCustomer(customerToAdd);
      System.out.println("Customer Added Successfully: " + customerToAdd);//If customer added successfully, you will see this message.
      adminFacade.addCustomer(customerToAdd2);
      System.out.println("Customer Added Successfully: " + customerToAdd2);//If customer added successfully, you will see this message.
      adminFacade.addCustomer(customerToAdd3);
      System.out.println("Customer Added Successfully: " + customerToAdd3);//If customer added successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Add customer test (fail customer email already exists):");
      adminFacade.addCustomer(customerToAdd);
      System.out.println("Customer Added Successfully: " + customerToAdd);//If customer added successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void updateCustomerTest() {
    Customer customerToUpdate= new Customer(1, "Doron", "Berger", "doronbrgr222@gmail.com", "MillieIsTheBest222");
    Customer customerToUpdate2= new Customer(6, "Doron", "Berger", "doronbrgr@gmail.com", "MillieIsTheBest");
    Customer customerToUpdate3= new Customer(2, "Moran", "Ariel", "doronbrgr222@gmail.com", "ArbelIsTheBest222");

    try {
      System.out.println("Update customer test (success):");
      adminFacade.updateCustomer(customerToUpdate);
      System.out.println("Customer Updated Successfully: " + customerToUpdate);//If customer updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Update customer test (fail customer doesn't exist):");
      adminFacade.updateCustomer(customerToUpdate2);
      System.out.println("Company Updated Successfully: " + customerToUpdate2);//If customer updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Update customer test (fail customer email already exists):");
      adminFacade.updateCustomer(customerToUpdate3);
      System.out.println("Company Updated Successfully: " + customerToUpdate3);//If customer updated successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void deleteCustomerTest(){
    try {
      System.out.println("Delete customer test (success):");
      adminFacade.deleteCustomer(2);
      System.out.println("Customer Deleted Successfully");//If customer deleted successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try {
      System.out.println("Delete customer test (fail customer does not exist):");
      adminFacade.deleteCustomer(7);
      System.out.println("Customer Deleted Successfully");//If customer deleted successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void getAllCustomersTest() {
    System.out.println("Get All Customers test (success)");
    System.out.println(adminFacade.getAllCustomers());
  }

  public static void getOneCustomerTest() {
    try{
      System.out.println("Get One Customer test (success)");
      Customer customer = adminFacade.getOneCustomer(1);
      System.out.println("Here is your Customer: " + customer);//If customer got successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }

    try{
      System.out.println("Get One Customer test (fail customer doesn't exist)");
      Customer customer2 = adminFacade.getOneCustomer(8);
      System.out.println("Here is your Customer: " + customer2);//If customer got successfully, you will see this message.
    } catch (CouponSystemException e) {
      System.err.println(e.getMessage());
    }
  }

}
