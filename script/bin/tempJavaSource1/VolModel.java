package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.database.Postgres;
import org.framework.annotation.FieldParamName;

import com.model.AvionModel;
import com.model.VilleModel;

public class VolModel {
    private int id;

    @FieldParamName( value = "avion",foreign=true)
    private AvionModel avion;

    @FieldParamName("dateHeureVol")
    private Timestamp dateHeureVol;

    @FieldParamName(value =  "depart",foreign=true)
    private VilleModel depart;

    @FieldParamName( value = "arrive",foreign=true)
    private VilleModel arrive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AvionModel getAvion() {
        return avion;
    }

    public void setAvion(AvionModel avion) {
        this.avion = avion;
    }

    public Timestamp getDateHeureVol() {
        return dateHeureVol;
    }

    public void setDateHeureVol(Timestamp dateHeureVol) {
        this.dateHeureVol = dateHeureVol;
    }

    public VilleModel getDepart() {
        return depart;
    }

    public void setDepart(VilleModel depart) {
        this.depart = depart;
    }

    public VilleModel getArrive() {
        return arrive;
    }

    public void setArrive(VilleModel arrive) {
        this.arrive = arrive;
    }

    public VolModel() {
    }

    public VolModel(int id, AvionModel avion, Timestamp dateHeureVol, VilleModel depart, VilleModel arrive) {
        this.id = id;
        this.avion = avion;
        this.dateHeureVol = dateHeureVol;
        this.depart = depart;
        this.arrive = arrive;
    }

    public VolModel(AvionModel avion, Timestamp dateHeureVol, VilleModel depart, VilleModel arrive) {
        this.avion = avion;
        this.dateHeureVol = dateHeureVol;
        this.depart = depart;
        this.arrive = arrive;
    }

    public List<VolModel> getAll() throws Exception {
        List<VolModel> vols = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = null;

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        AvionModel avion = new AvionModel();
        VilleModel ville = new VilleModel();

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve all records from the "depense" table
            query = "SELECT * FROM vol";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                VolModel vol = new VolModel(
                        rs.getInt("id"),
                        avion.getbyId(rs.getInt("id_avion")),
                        rs.getTimestamp("dateHeureVol"),
                        ville.getbyId(rs.getInt("depart")),
                        ville.getbyId(rs.getInt("arrive")));
                vols.add(vol);
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
        return vols;
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
            query = "INSERT INTO vol(id_avion, dateheurevol, depart, arrive) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the prepared statement
            stmt.setInt(1, this.getAvion().getId());
            stmt.setTimestamp(2, this.getDateHeureVol());
            stmt.setInt(3, this.getDepart().getId());
            stmt.setInt(4, this.getArrive().getId());

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

}
