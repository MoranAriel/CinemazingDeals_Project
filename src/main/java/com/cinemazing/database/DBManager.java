package com.cinemazing.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {
  // Step 1
  private static final DBManager instance = new DBManager();

  // Step 3
  public static DBManager getInstance() {
    return instance;
  }

  // Step 2
  private DBManager() {

  }

  // connection to mysql
  public static final String URL = "jdbc:mysql://localhost:3306/";
  public static final String SQL_USER = "root";
  public static final String SQL_PASS = "12345678";

  // DataBase
  public static final String DB = "`cinemazing_deals`";

  // general method to create schemas or tables.
  public static void createX(String str) {
    Connection connection = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(str);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
      ;
    } finally {
      ConnectionPool.getInstance().restoreConnection(connection);
    }
  }

  public static void createSchema() {
    createX(CREATE_SCHEMA);
  }

  public static void dropSchema() {
    createX(DROP_SCHEMA);
  }

  public static void createTable(String str) {
    createX(str);
  }

  // method creating the database (Schema and table)
  public static void createDataBase() {
    dropSchema();
    createSchema();
    createTable(CREATE_TABLE_COMPANIES);
    createTable(CREATE_TABLE_CATEGORIES);
    createTable(CREATE_TABLE_CUSTOMERS);
    createTable(CREATE_TABLE_COUPONS);
    createTable(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    addCategory(ADD_CATEGORY, "New Gear");
    addCategory(ADD_CATEGORY, "Rental Gear");
    addCategory(ADD_CATEGORY, "Production");
    addCategory(ADD_CATEGORY, "Post Production");
    addCategory(ADD_CATEGORY, "Movie Theaters");
    addCategory(ADD_CATEGORY, "Streaming");
    addCategory(ADD_CATEGORY, "DVD and Blueray");
  }

  // string for creating schema
  public static final String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS " + DB + " ;";

  public static final String DROP_SCHEMA = "DROP SCHEMA IF EXISTS " + DB;

  // string for creating companies table
  public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS " + DB + ".`companies` (" +
      " `ID` INT NOT NULL AUTO_INCREMENT," +
      " `NAME` VARCHAR(45) NOT NULL," +
      " `EMAIL` VARCHAR(45) NOT NULL," +
      " `PASSWORD` VARCHAR(45) NOT NULL," +
      " PRIMARY KEY (`ID`)," +
      " UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) VISIBLE," +
      " UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) VISIBLE);";

  // string for creating customers table
  public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS " + DB + ".`customers` (" +
      " `ID` INT NOT NULL AUTO_INCREMENT," +
      " `FIRST_NAME` VARCHAR(45) NOT NULL," +
      " `LAST_NAME` VARCHAR(45) NOT NULL," +
      " `EMAIL` VARCHAR(45) NOT NULL," +
      " `PASSWORD` VARCHAR(45) NOT NULL," +
      " PRIMARY KEY (`ID`)," +
      " UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) VISIBLE);";

  // string for creating categories table
  public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " + DB + ".`categories` (" +
      " `ID` INT NOT NULL AUTO_INCREMENT," +
      " `NAME` VARCHAR(45) NOT NULL," +
      " PRIMARY KEY (`ID`)," +
      " UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) VISIBLE);";

  // string for creating coupons table
  public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS " + DB + ".`coupons` (" +
      " `ID` INT NOT NULL AUTO_INCREMENT," +
      " `COMPANY_ID` INT NOT NULL," +
      " `CATEGORY_ID` INT NOT NULL," +
      " `TITLE` VARCHAR(45) NOT NULL," +
      " `DESCRIPTION` VARCHAR(45) NOT NULL," +
      " `START_DATE` DATETIME NOT NULL," +
      " `END_DATE` DATETIME NOT NULL," +
      " `AMOUNT` INT NULL," +
      " `PRICE` DECIMAL NOT NULL," +
      " `IMAGE` VARCHAR(45) NOT NULL," +
      " PRIMARY KEY (`ID`)," +
      " UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE)";

  // string for creating customers vs coupons table
  public static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS " + DB
      + ".`customers_vs_coupons` (" +
      " `CUSTOMER_ID` INT NOT NULL," +
      " `COUPON_ID` INT NOT NULL," +
      " PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`))";

  public static void addCategory(String add, String spec) {
    Connection connection = null;
    try {
      connection = ConnectionPool.getInstance().getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(add);
      preparedStatement.setString(1, spec);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
      ;
    } finally {
      ConnectionPool.getInstance().restoreConnection(connection);
    }
  }

  // CRUD

  public static final String ADD_CATEGORY = "INSERT INTO " + DB + ".`categories` " +
      "(name) VALUES(?)";

  public static final String ADD_COUPON = "INSERT INTO " + DB + ".`coupons` " +
      "(company_id,category_id,title,description,start_date,end_date,amount,price,image) " +
      "VALUES(?,?,?,?,?,?,?,?,?)";

  public static final String ADD_CUSTOMER_VS_COUPON = "INSERT INTO " + DB + ".`customers_vs_coupons` " +
      "(customer_id,coupon_id) VALUES(?,?)";

  public static final String DELETE_COMPANY = "DELETE FROM " + DB + ".`companies` WHERE (`ID` = '?')";

}
