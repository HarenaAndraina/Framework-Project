package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.AvionModel;
import com.database.Postgres;
import com.model.TypeSiegeModel;

public class AvionSiegeModel {
    private int id;
    private TypeSiegeModel siege;
    private AvionModel avion;
    private double prix;
    private int nbMax;
    private Double promotion;
   
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public TypeSiegeModel getSiege() {
        return siege;
    }
    public void setSiege(TypeSiegeModel siege) {
        this.siege = siege;
    }
    public AvionModel getAvion() {
        return avion;
    }
    public void setAvion(AvionModel avion) {
        this.avion = avion;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public int getNbMax() {
        return nbMax;
    }
    public void setNbMax(int nbMax) {
        this.nbMax = nbMax;
    }
    public AvionSiegeModel() {
    }

    public AvionSiegeModel(int id, TypeSiegeModel siege, AvionModel avion, double prix, int nbMax) {
        this.id = id;
        this.siege = siege;
        this.avion = avion;
        this.prix = prix;
        this.nbMax = nbMax;
    }

    public AvionSiegeModel(TypeSiegeModel siege, AvionModel avion, double prix, int nbMax) {
        this.siege = siege;
        this.avion = avion;
        this.prix = prix;
        this.nbMax = nbMax;
    }

    public List<AvionSiegeModel> getByIdAvion(int id) throws Exception{
        List<AvionSiegeModel> avionSieges = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        TypeSiegeModel typeSiege=new TypeSiegeModel();
        AvionModel avion=new AvionModel();

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM avion_siege WHERE id_avion = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                // Populate the Client object with the retrieved data
                AvionSiegeModel avionSiege = new AvionSiegeModel(
                        rs.getInt("id"),
                        typeSiege.getbyId(rs.getInt("id_siege")),
                        avion.getbyId(rs.getInt("id_avion")),
                        rs.getDouble("prix"),
                        rs.getInt("nbMax"));

                        avionSieges.add(avionSiege);
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
        if (avionSieges == null) {
            throw new Exception("avion siege null");
        }
        return avionSieges;
    }
    
    public AvionSiegeModel getbyId(int id) throws Exception {
        AvionSiegeModel as = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        AvionModel avion = new AvionModel();
        TypeSiegeModel siege = new TypeSiegeModel();

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM avion_siege WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                as = new AvionSiegeModel(
                    rs.getInt("id"),
                    siege.getbyId(rs.getInt("id_siege")),
                    avion.getbyId(rs.getInt("id_avion")),
                    rs.getDouble("prix"),
                    rs.getInt("nbMax"));
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
        if (as == null) {
            throw new Exception("avion siege null");
        }
        return as;
    }
    public Double getPromotion() {
        return promotion;
    }
    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }
    
    
    
    
}
