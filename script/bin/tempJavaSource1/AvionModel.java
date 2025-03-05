package com.model;

import com.database.Postgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.database.Postgres;


public class AvionModel {
    private int id;
    private String num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public AvionModel() {
    }

    public AvionModel(int id, String num) {
        this.id = id;
        this.num = num;
    }

    public AvionModel(String num) {
        this.num = num;
    }

    public AvionModel getbyId(int id) throws Exception {
        AvionModel avion = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM avion WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                avion = new AvionModel(
                        rs.getInt("id"),
                        rs.getString("num"));
            }
        } catch (Exception e) {
            throw new Exception("Error executing SQL statement: " + e.getMessage() + ". SQL Statement: " + query, e);
        } finally {
            // Close resources in the reverse order of their creation
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        if (avion == null) {
            throw new Exception("Client null");
        }
        return avion;
    }

    public List<AvionModel> getAll() throws Exception {
        List<AvionModel> avions = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = null;

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve all records from the "depense" table
            query = "SELECT * FROM avion";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AvionModel avion = new AvionModel(
                        rs.getInt("id"),
                        rs.getString("num"));
                avions.add(avion);
            }
        } catch (Exception e) {
            throw new Exception("Error executing SQL statement: " + e.getMessage() + ". SQL Statement: " + query, e);
        } finally {
            // Close resources in the reverse order of their creation
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return avions;
    }

}
