package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.FieldParamName;
import com.database.Postgres;

import com.model.AvionSiegeModel;
import com.model.VolModel;

public class PromotionVolModel {
    private int id;
    @FieldParamName(value = "vol",foreign = true)
    private VolModel vol;

    @FieldParamName(value =  "siege",foreign = true)
    private AvionSiegeModel avionSiege;

    @FieldParamName(value = "pourcentage" , foreign = true)
    private double pourcentage;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public VolModel getVol() {
        return vol;
    }
    public void setVol(VolModel vol) {
        this.vol = vol;
    }
    public AvionSiegeModel getAvionSiege() {
        return avionSiege;
    }
    public void setAvionSiege(AvionSiegeModel avionSiege) {
        this.avionSiege = avionSiege;
    }
    public double getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }
    public PromotionVolModel() {
    }
    public PromotionVolModel(int id, VolModel vol, AvionSiegeModel avionSiege, double pourcentage) {
        this.id = id;
        this.vol = vol;
        this.avionSiege = avionSiege;
        this.pourcentage = pourcentage;
    }
    public PromotionVolModel(VolModel vol, AvionSiegeModel avionSiege, double pourcentage) {
        this.vol = vol;
        this.avionSiege = avionSiege;
        this.pourcentage = pourcentage;
    }

    public void insert() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        try {
            conn = Postgres.getConnection(username, password, databaseName);
            conn.setAutoCommit(false);

            // Query to insert the new commande into the database and retrieve the generated
            // key
            query = "INSERT INTO promotion_vol(id_vol, id_avion_siege,pourcentage) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the prepared statement
            stmt.setInt(1, this.getVol().getId());
            stmt.setInt(2, this.getAvionSiege().getId());
            stmt.setDouble(3, this.getPourcentage());

            // Execute the insert
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Insert failed, no rows affected.");
            }

            // Retrieve the generated key
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                // Do something with the generated ID if needed
            } else {
                throw new Exception("Insert failed, couldn't retrieve generated key.");
            }

            // Commit the transaction
            conn.commit();

        } catch (Exception e) {
            // Handle exceptions and rollback the transaction if needed
            if (conn != null) {
                conn.rollback();
            }

            throw new Exception("Error executing SQL statement: " + e.getMessage() + ". SQL Statement: " + query, e);
        } finally {
            // Restore auto-commit to true and close resources
            if (conn != null) {
                conn.setAutoCommit(true);
                if (stmt != null) {
                    stmt.close();
                }
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                conn.close();
            }
        }
    }

    public List<PromotionVolModel> getByIdVol(int id) throws Exception{
        List<PromotionVolModel> promotionVols = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        VolModel vol=new VolModel();
        AvionSiegeModel avionSiege =new AvionSiegeModel(); 

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM promotion_vol WHERE id_vol = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                // Populate the Client object with the retrieved data
                PromotionVolModel promotionVol = new PromotionVolModel(
                        rs.getInt("id"),
                        vol.getbyId(rs.getInt("id_vol")),
                        avionSiege.getbyId(rs.getInt("id_avion_siege")),
                        rs.getDouble("pourcentage"));

                        promotionVols.add(promotionVol);
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
        if (promotionVols == null) {
            throw new Exception("Client null");
        }
        return promotionVols;
    }

    
}
