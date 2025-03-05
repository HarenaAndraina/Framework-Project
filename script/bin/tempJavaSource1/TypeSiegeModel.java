package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.database.Postgres;


public class TypeSiegeModel {
    private int id;
    private String nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

   

    public TypeSiegeModel(String nom) {
        this.nom = nom;
    }

    public TypeSiegeModel(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public TypeSiegeModel() {
    }

    public TypeSiegeModel getbyId(int id) throws Exception {
        TypeSiegeModel typeSiege = null;
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
            query = "SELECT * FROM type_siege WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                typeSiege = new TypeSiegeModel(
                        rs.getInt("id"),
                        rs.getString("nom"));
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
        if (typeSiege == null) {
            throw new Exception("Client null");
        }
        return typeSiege;
    }
    
    public List<TypeSiegeModel> getAll() throws Exception {
        List<TypeSiegeModel> typeSieges = new ArrayList<>();

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
            query = "SELECT * FROM type_siege";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TypeSiegeModel typeSiege = new TypeSiegeModel(
                        rs.getInt("id"),
                        rs.getString("nom"));
                typeSieges.add(typeSiege);
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
        return typeSieges;
    }
}
