package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {
    public static Connection getConnection(String username,String password,String databaseName) throws Exception
    {
        Connection conn=null;

        try {
           String jdbcUrl = "jdbc:postgresql://localhost:5432/"+databaseName;
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            throw new Exception("Connection postgres jdbc failed: "+e.getMessage());
        }
        return conn;
    } 
}
