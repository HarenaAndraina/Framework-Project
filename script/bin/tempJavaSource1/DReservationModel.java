package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import org.framework.annotation.FieldParamName;
import com.database.Postgres;
import com.model.VolModel;

public class DReservationModel {
    private int id;

    @FieldParamName("dateHeure")
    private Timestamp dateHeure;

    @FieldParamName("vol")
    private VolModel vol;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Timestamp getDateHeure() {
        return dateHeure;
    }
    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }
   

    public DReservationModel(Timestamp dateHeure, VolModel vol) {
        this.dateHeure = dateHeure;
        this.vol = vol;
    }
    public DReservationModel(int id, Timestamp dateHeure, VolModel vol) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.vol = vol;
    }
    public DReservationModel() {
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
            query = "INSERT INTO d_reservation_valide(dateHeure,id_vol) VALUES (?,?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the prepared statement
            stmt.setTimestamp(1, this.getDateHeure());
            stmt.setInt(2, this.getVol().getId());


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

    public DReservationModel getLastId(int id_vol) throws Exception {
        DReservationModel dernierRes = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        VolModel vol=new VolModel(); 

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM d_reservation_valide WHERE id_vol= ? ORDER BY id DESC LIMIT 1";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_vol);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                dernierRes = new DReservationModel(
                        rs.getInt("id"),
                        rs.getTimestamp("dateHeure"),
                        vol.getbyId(rs.getInt("id_vol"))
                        );
                        
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
        if (dernierRes == null) {
            throw new Exception("Client null");
        }
        return dernierRes;
    }
    public VolModel getVol() {
        return vol;
    }
    public void setVol(VolModel vol) {
        this.vol = vol;
    }
}
