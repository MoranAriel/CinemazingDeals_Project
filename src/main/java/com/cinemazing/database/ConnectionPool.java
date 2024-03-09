package com.cinemazing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private static final int NUMBER_OF_CONNECTIONS = 10;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        System.out.println("Welcome to Cinemazing Deals!");
        openAllConnections();
    }

    private void openAllConnections() throws SQLException {
        //create connections according to Number of Connections
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            //create connection
            Connection connection = DriverManager.getConnection(DBManager.URL, DBManager.SQL_USER, DBManager.SQL_PASS);
            //push the new connection into stack "connections".
            connections.push(connection);
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public static ConnectionPool getInstance(){
        //Connection Pool Singleton
        if (instance==null){
            synchronized (ConnectionPool.class){
                if(instance==null){
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());;
                    }
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections){
            if (connections.isEmpty()){
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void restoreConnection(Connection connection){
        synchronized (connections){
            connections.push(connection);
            //notify that we got back a connection for the user
            connections.notify();
        }
    }
}
