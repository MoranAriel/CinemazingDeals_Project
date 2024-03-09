package com.cinemazing.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinemazing.beans.Coupon;
import com.cinemazing.database.ConnectionPool;
import com.cinemazing.database.DBManager;

public class CouponsDBDAO implements CouponsDAO {

  private ConnectionPool connectionPool;

  public CouponsDBDAO() {
    this.connectionPool = ConnectionPool.getInstance();
  }

  @Override
  public void addCoupon(Coupon coupon) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "INSERT INTO " + DBManager.DB + ".`coupons` " +
          "(company_id,category_id,title,description,start_date,end_date,amount,price,image) " +
          "VALUES(?,?,?,?,?,?,?,?,?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setInt(1, coupon.getCompanyID());
      preparedStatement.setInt(2, coupon.getCategory().getId());
      preparedStatement.setString(3, coupon.getTitle());
      preparedStatement.setString(4, coupon.getDescription());
      preparedStatement.setDate(5, Date.valueOf(coupon.getStartDate()));
      preparedStatement.setDate(6, Date.valueOf(coupon.getEndDate()));
      preparedStatement.setInt(7, coupon.getAmount());
      preparedStatement.setDouble(8, coupon.getPrice());
      preparedStatement.setString(9, coupon.getImage());
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public void updateCoupon(Coupon coupon) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "UPDATE " + DBManager.DB
          + ".`coupons` SET `CATEGORY_ID` = '" + coupon.getCategory().getId() + "', " +
          "`TITLE` = '" + coupon.getTitle() + "', " +
          "`DESCRIPTION` = '" + coupon.getDescription() + "', " +
          "`START_DATE` = '" + Date.valueOf(coupon.getStartDate()) + "', " +
          "`END_DATE` = '" + Date.valueOf(coupon.getEndDate()) + "', " +
          "`AMOUNT` = '" + coupon.getAmount() + "', " +
          "`PRICE` = '" + coupon.getPrice() + "', " +
          "`IMAGE` = '" + coupon.getImage() + "' " +
          "WHERE (`ID` = '" + coupon.getId() + "')";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public void deleteCoupon(int couponID) {
    Connection connection = null;
    String query = "DELETE FROM " + DBManager.DB
        + ".`coupons` WHERE (`ID` = '" + couponID + "')";
    try {
      connection = connectionPool.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public List<Coupon> getAllCoupons() {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM " + DBManager.DB + ".`coupons`";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public Coupon getOneCoupon(int couponID) {
    Connection connection = null;
    Coupon result = new Coupon();

    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM " + DBManager.DB + ".`coupons` WHERE (`ID` = '" + couponID + "')";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        result.setId(resultSet.getInt("ID"));
        result.setCompanyID(resultSet.getInt("COMPANY_ID"));
        result.setCategory((resultSet.getInt("CATEGORY_ID")));
        result.setTitle(resultSet.getString("TITLE"));
        result.setDescription(resultSet.getString("DESCRIPTION"));
        result.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        result.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        result.setAmount(resultSet.getInt("AMOUNT"));
        result.setPrice(resultSet.getInt("PRICE"));
        result.setImage(resultSet.getString("IMAGE"));
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }


  @Override
  public void addCouponPurchase(int customerID, int couponID) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "INSERT INTO " + DBManager.DB + ".`customers_vs_coupons` " +
          "(customer_id,coupon_id) VALUES(?,?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, customerID);
      preparedStatement.setInt(2, couponID);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public void deleteCouponPurchase(int customerID, int couponID) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "DELETE FROM " + DBManager.DB + ".`customers_vs_coupons` " +
          "WHERE (`CUSTOMER_ID` = '" + customerID + "') and (`COUPON_ID` = '" + couponID + "')";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }
//
//  public Map<Integer, ArrayList<Integer>> getCouponPurchaseHistory(List<Customer> customers) {
//    Connection connection = null;
//    Map<Integer, ArrayList<Integer>> couponPurchaseHistory = new HashMap<>();
//    ArrayList<Integer> customerList = new ArrayList<>();
//    for (Customer customer : customers) {
//      int id = customer.getId();
//      customerList.add(id);
//    }
//    try {
//      connection = connectionPool.getConnection();
//      String query = "SELECT * FROM " + DBManager.DB + ".`customers_vs_coupons`";
//      PreparedStatement preparedStatement = connection.prepareStatement(query);
//      ResultSet resultSet = preparedStatement.executeQuery();
//      for (Integer customer : customerList) {
//        ArrayList<Integer> purchasedCoupons = new ArrayList<>();
//        while (resultSet.next()) {
//          if (customer == resultSet.getInt("CUSTOMER_ID")) {
//            purchasedCoupons.add(resultSet.getInt("COUPON_ID"));
//          }
//        }
//
//        couponPurchaseHistory.put(customer, purchasedCoupons);
//        resultSet = preparedStatement.executeQuery();
//      }
//
//    } catch (InterruptedException | SQLException e) {
//      System.out.println(e.getMessage());
//    } finally {
//      connectionPool.restoreConnection(connection);
//    }
//    return couponPurchaseHistory;
//  }

  @Override
  public List<Coupon> getCompanyCoupons(int companyID) {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM " + DBManager.DB + ".`coupons` WHERE COMPANY_ID = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, companyID);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public void deleteCouponPurchaseByCouponId(int couponID) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "DELETE FROM " + DBManager.DB + ".`customers_vs_coupons` " +
          "WHERE `COUPON_ID` = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, couponID);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public void deleteExpiredCoupons() {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();

      String queryPurchase = "DELETE FROM " + DBManager.DB
          + ".`customers_vs_coupons` WHERE COUPON_ID IN (SELECT ID FROM " + DBManager.DB
          + ".`coupons` WHERE END_DATE< CURDATE());";
          
      PreparedStatement preparedStatement1 = connection.prepareStatement(queryPurchase);
      preparedStatement1.execute();

      String queryCoupon = "DELETE FROM " + DBManager.DB + ".`coupons`  WHERE END_DATE < CURDATE()";

      PreparedStatement preparedStatement2 = connection.prepareStatement(queryCoupon);
      preparedStatement2.execute();

    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public void deleteCouponPurchaseByCustomerId(int customerID) {
    Connection connection = null;

    try {
      connection = connectionPool.getConnection();
      String query = "DELETE FROM " + DBManager.DB + ".`customers_vs_coupons` " +
          "WHERE `CUSTOMER_ID` = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, customerID);
      preparedStatement.execute();
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
  }

  @Override
  public boolean isCouponExpired(int couponID) {
    Connection connection = null;
    boolean result = false;

    try {
      connection = connectionPool.getConnection();

      String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`coupons`"
                    + "WHERE id = ? AND END_DATE < CURDATE()) AS res;";

      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setInt(1, couponID);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        result = resultSet.getBoolean("res");
      }

    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public boolean isCouponExists(int couponID) {
    Connection connection = null;
    boolean result = false;

    try {
        connection = connectionPool.getConnection();

        String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`coupons`" + "WHERE ID = ?) AS res;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, couponID);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result = resultSet.getBoolean("res");
        }

    } catch (InterruptedException | SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        connectionPool.restoreConnection(connection);
    }
    return result;
}

  @Override
  public boolean isCouponExistsByCustomerID(int customerID, int couponID) {
    Connection connection = null;
    boolean result = false;

    try {
      connection = connectionPool.getConnection();

      String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`customers_vs_coupons`" + "WHERE CUSTOMER_ID = ? AND COUPON_ID = ? ) AS res;";

      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setInt(1, customerID);
      preparedStatement.setInt(2, couponID);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        result = resultSet.getBoolean("res");
      }

    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;

  }

  @Override
  public boolean isCouponExistsByTitleAndCompanyId(String title, int companyID) {
    Connection connection = null;
    boolean result = false;

    try {
        connection = connectionPool.getConnection();

        String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`coupons`" + "WHERE TITLE = ? AND COMPANY_ID = ?) AS res;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, companyID);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result = resultSet.getBoolean("res");
        }

    } catch (InterruptedException | SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public List<Coupon> getCompanyCouponsByCategory(int companyID, int categoryID) {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM " + DBManager.DB + ".`coupons` WHERE COMPANY_ID = ? AND CATEGORY_ID = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, companyID);
      preparedStatement.setInt(2, categoryID);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public List<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice) {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM " + DBManager.DB + ".`coupons` WHERE COMPANY_ID = ? AND PRICE <= ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, companyID);
      preparedStatement.setDouble(2, maxPrice);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }

  @Override
  public List<Coupon> getCustomerCoupons(int customerID) {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query = "SELECT * FROM" + DBManager.DB + ".coupons inner JOIN" + DBManager.DB + ".customers_vs_coupons ON customers_vs_coupons.COUPON_ID = coupons.ID WHERE (`CUSTOMER_ID` = ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, customerID);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }



  @Override
  public List<Coupon> getCustomerCouponsByCategory(int customerID, int categoryID) {
    Connection connection = null;
    List<Coupon> result = new ArrayList<>();
    try {
      connection = connectionPool.getConnection();
      String query ="SELECT * FROM" + DBManager.DB + ".coupons inner JOIN" + DBManager.DB + ".customers_vs_coupons ON customers_vs_coupons.COUPON_ID = coupons.ID WHERE (`CUSTOMER_ID` = ? AND CATEGORY_ID = ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, customerID);
      preparedStatement.setInt(2, categoryID);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt("ID"));
        coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
        coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
        coupon.setTitle(resultSet.getString("TITLE"));
        coupon.setDescription(resultSet.getString("DESCRIPTION"));
        coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
        coupon.setAmount((resultSet.getInt("AMOUNT")));
        coupon.setPrice(resultSet.getInt("PRICE"));
        coupon.setImage(resultSet.getString("IMAGE"));
        result.add(coupon);
      }
    } catch (InterruptedException | SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionPool.restoreConnection(connection);
    }
    return result;
  }



    @Override
  public List<Coupon> getCustomerCouponsByMaxPrice(int customerID, double maxPrice) {
      Connection connection = null;
      List<Coupon> result = new ArrayList<>();
      try {
        connection = connectionPool.getConnection();
        String query = "SELECT * FROM" + DBManager.DB + ".coupons inner JOIN" + DBManager.DB + ".customers_vs_coupons ON customers_vs_coupons.COUPON_ID = coupons.ID WHERE (`CUSTOMER_ID` = ? AND PRICE <= ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setDouble(2, maxPrice);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          Coupon coupon = new Coupon();
          coupon.setId(resultSet.getInt("ID"));
          coupon.setCompanyID((resultSet.getInt("COMPANY_ID")));
          coupon.setCategory((resultSet.getInt("CATEGORY_ID")));
          coupon.setTitle(resultSet.getString("TITLE"));
          coupon.setDescription(resultSet.getString("DESCRIPTION"));
          coupon.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
          coupon.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
          coupon.setAmount((resultSet.getInt("AMOUNT")));
          coupon.setPrice(resultSet.getInt("PRICE"));
          coupon.setImage(resultSet.getString("IMAGE"));
          result.add(coupon);
        }
      } catch (InterruptedException | SQLException e) {
        System.out.println(e.getMessage());
      } finally {
        connectionPool.restoreConnection(connection);
      }
      return result;
  }
}
