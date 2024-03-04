package com.cinemazing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinemazing.beans.Company;
import com.cinemazing.database.ConnectionPool;
import com.cinemazing.database.DBManager;

public class CompaniesDBDAO implements CompaniesDAO {


    private ConnectionPool connectionPool;

    // Constructor that takes a Connection
    public CompaniesDBDAO() {

        this.connectionPool = ConnectionPool.getInstance();
    }

    // Implement methods from CompaniesDAO interface
    @Override

    public boolean isCompanyExistsByEmailAndPassword(String email, String password) {
        Connection connection = null;
        boolean result = false;

        try {
            connection = connectionPool.getConnection();

            String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`companies`" + "WHERE email = ? AND password = ?) AS res;";

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
    public void addCompany(Company company) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "INSERT INTO " + DBManager.DB + ".`companies` " + "(name,email,password) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, company.getPassword());
            preparedStatement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }

    }

    @Override
    public void updateCompany(Company company) {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "UPDATE " + DBManager.DB + ".`companies` " + "SET `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setString(2, company.getPassword());
            preparedStatement.setInt(3, company.getId());
            preparedStatement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCompany(int companyID) {
        Connection connection = null;
        String query = "DELETE FROM " + DBManager.DB + ".`companies` WHERE (`ID` = '" + companyID + "')";
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
    public List<Company> getAllCompanies() {
        Connection connection = null;
        List<Company> result = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM " + DBManager.DB + ".`companies`";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setEmail(resultSet.getString("email"));
                company.setName(resultSet.getString("name"));
                company.setPassword(resultSet.getString("password"));
                result.add(company);
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return result;
    }

    @Override
    public Company getOneCompany(int companyID) {
        Connection connection = null;
        Company result = new Company();


        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM " + DBManager.DB + ".`companies` WHERE (`ID` = '" + companyID + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setEmail(resultSet.getString("email"));
                result.setName(resultSet.getString("name"));
                result.setPassword(resultSet.getString("password"));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return result;
    }

    @Override
    public boolean isCompanyExistsById(int id) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`companies`" + "WHERE id = ?) AS res;";

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
    public boolean isCompanyExistsByEmail(String email) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`companies`" + "WHERE email = ?) AS res;";

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
    public boolean isCompanyExistsByName(String name) {
      Connection connection = null;
      boolean result = false;

      try {
          connection = connectionPool.getConnection();

          String query = "SELECT EXISTS (SELECT * FROM" + DBManager.DB + ".`companies`" + "WHERE name = ?) AS res;";

          PreparedStatement preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, name);
          ResultSet resultSet = preparedStatement.executeQuery();

          while (resultSet.next()) {
              result = resultSet.getBoolean("res");
          }

      } catch (InterruptedException | SQLException e) {
          System.out.println(e.getMessage());
      } finally {
          connectionPool.restoreConnection(connection);
      }
      return result;    }

}
