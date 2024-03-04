package com.cinemazing.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DBTools {

    public static boolean runQuery(String sql) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static boolean runQuery(String sql, Map<Integer, Object> params) {
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (Integer) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof Date) {
                        statement.setDate(key, (Date)value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double)value);
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean)value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float)value);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static ResultSet runQueryForResult (String sql, Map<Integer, Object> params){
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (Integer) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof Date) {
                        statement.setDate(key, (Date)value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double)value);
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean)value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float)value);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            statement.execute();
            return statement.executeQuery();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }
}
