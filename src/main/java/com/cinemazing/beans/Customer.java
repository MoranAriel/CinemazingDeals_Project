package com.cinemazing.beans;

import java.util.ArrayList;
import java.util.List;

public class Customer {

  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<Coupon> coupons;
//  private static int counter = 1;

  public Customer(){
    this.coupons = new ArrayList<>();
  }

  public Customer(int id, String firstName, String lastName, String email,
                  String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.coupons = new ArrayList<>();
  }


  public Customer(String firstName, String lastName, String email,
      String password) {
//    this.id = counter;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.coupons = new ArrayList<>();
//    counter++;
  }

  public Customer(String firstName, String lastName, String email,
                  String password, List<Coupon> coupons) {
//    this.id = counter;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.coupons = coupons;
//    counter++;
  }




  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Coupon> getCoupons() {
    return coupons;
  }

  public void setCoupons(List<Coupon> coupons) {
    this.coupons = coupons;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", coupons=" + coupons +
        '}';
  }

}
