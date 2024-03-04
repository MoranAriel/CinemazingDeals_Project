package com.cinemazing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinemazing.database.DBManager;
import com.cinemazing.beans.Customer;
import com.cinemazing.database.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {

    private ConnectionPool connectionPool;
//    private static List<Customer> customers = new ArrayList<>();

    public CustomersDBDAO() {
      this.connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public boolean isCustomerExistsByEmailAndPassword(String email, String password) {

        Connection connection = null;
        boolean result = false;

        try {
            connection = connectionPool.getConnection();

            String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`customers`" + "WHERE email = ? AND password = ?) AS res;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

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
    public void addCustomer(Customer customer) {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "INSERT INTO " + DBManager.DB + ".`customers` " +
                    "(first_name,last_name,email,password) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.execute();
         //   UserLists.getAllCustomers().add(customer);
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "UPDATE " + DBManager.DB + ".`customers`" +
                    " SET `FIRST_NAME` = ?, `LAST_NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "DELETE FROM " + DBManager.DB + ".`customers` WHERE (`ID` = '" + customerID + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        Connection connection = null;
        List<Customer> result = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM " + DBManager.DB + ".`customers`";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("ID"));
                customer.setFirstName(resultSet.getString("FIRST_NAME"));
                customer.setLastName(resultSet.getString("LAST_NAME"));
                customer.setEmail(resultSet.getString("EMAIL"));
                customer.setPassword(resultSet.getString("PASSWORD"));
                result.add(customer);
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return result;
    }

    @Override
    public Customer getOneCustomer(int customerID) {

        Connection connection = null;
        Customer result = new Customer();

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM " + DBManager.DB + ".`customers` WHERE (`ID` = '" + customerID + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.setId(resultSet.getInt("ID"));
                result.setFirstName(resultSet.getString("FIRST_NAME"));
                result.setLastName(resultSet.getString("LAST_NAME"));
                result.setEmail(resultSet.getString("EMAIL"));
                result.setPassword(resultSet.getString("PASSWORD"));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return result;
    }

    @Override
    public boolean isCustomerExistsByEmail(String email) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`customers`" + "WHERE EMAIL = ?) AS res;";

          PreparedStatement preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, email);

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
    public boolean isCustomerExistsById(int id) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`customers`" + "WHERE ID = ?) AS res;";

          PreparedStatement preparedStatement = connection.prepareStatement(query);

          preparedStatement.setInt(1, id);

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
    public boolean isCustomerExistsByEmailAndNotId(String email, int id) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`customers`" + "WHERE EMAIL = ? AND ID != ?) AS res;";

          PreparedStatement preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, email);
          preparedStatement.setInt(2, id);

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
}
