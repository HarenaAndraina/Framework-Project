package com.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.FieldParamName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.model.AvionSiegeModel;
import com.model.UtilisateurModel;
import com.model.VolModel;
import com.database.Postgres;


public class ReservationModel {
    private int id;
    @FieldParamName(value = "vol",foreign = true)
    private VolModel vol;
    @FieldParamName(value = "utilisateur",foreign = true)
    private UtilisateurModel utilisateur;
    @FieldParamName(value = "siege",foreign = true)
    private AvionSiegeModel avionSiege;
    @FieldParamName("dateHeure")
    private Timestamp dateHeureReserv;
    
    

    public ReservationModel(VolModel vol, UtilisateurModel utilisateur, AvionSiegeModel avionSiege,
            Timestamp dateHeureReserv) {
        this.vol = vol;
        this.utilisateur = utilisateur;
        this.avionSiege = avionSiege;
        this.dateHeureReserv = dateHeureReserv;
    }



    public ReservationModel(int id, VolModel vol, UtilisateurModel utilisateur, AvionSiegeModel avionSiege,
            Timestamp dateHeureReserv) {
        this.id = id;
        this.vol = vol;
        this.utilisateur = utilisateur;
        this.avionSiege = avionSiege;
        this.dateHeureReserv = dateHeureReserv;
    }



    public ReservationModel() {
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
            query = "INSERT INTO reservation(id_vol, id_utilisateur, id_avion_siege, dateheure) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the prepared statement
            stmt.setInt(1, this.getVol().getId());
            stmt.setInt(2, this.getUtilisateur().getId());
            stmt.setInt(3, this.getAvionSiege().getId());
            stmt.setTimestamp(4, this.getDateHeureReserv());

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



    public UtilisateurModel getUtilisateur() {
        return utilisateur;
    }



    public void setUtilisateur(UtilisateurModel utilisateur) {
        this.utilisateur = utilisateur;
    }



    public AvionSiegeModel getAvionSiege() {
        return avionSiege;
    }



    public void setAvionSiege(AvionSiegeModel avionSiege) {
        this.avionSiege = avionSiege;
    }



    public Timestamp getDateHeureReserv() {
        return dateHeureReserv;
    }



    public void setDateHeureReserv(Timestamp dateHeureReserv) {
        this.dateHeureReserv = dateHeureReserv;
    } 


    public List<ReservationModel> getByUser(int id) throws Exception {
        List<ReservationModel> reservations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = null;

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        UtilisateurModel utilisateur=new UtilisateurModel();
        AvionSiegeModel avionSiege=new AvionSiegeModel();
        VolModel vol=new VolModel();

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve all records from the "depense" table
            query = "SELECT * FROM reservation where id_utilisateur= ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationModel res = new ReservationModel(
                        rs.getInt("id"),
                        vol.getbyId(rs.getInt("id_vol")),
                        utilisateur.getbyId(rs.getInt("id_utilisateur")),
                        avionSiege.getbyId(rs.getInt("id_avion_siege")), 
                        rs.getTimestamp("dateHeure"));
                        reservations.add(res);
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
        return reservations;
    }
    public ReservationModel getbyId(int id) throws Exception {
        ReservationModel res = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "null";

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        UtilisateurModel utilisateur=new UtilisateurModel();
        AvionSiegeModel avionSiege=new AvionSiegeModel();
        VolModel vol=new VolModel();

        try {
            conn = Postgres.getConnection(username, password, databaseName);

            // Query to retrieve a specific client by ID
            query = "SELECT * FROM reservation WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                 res = new ReservationModel(
                    rs.getInt("id"),
                    vol.getbyId(rs.getInt("id_vol")),
                    utilisateur.getbyId(rs.getInt("id_utilisateur")),
                    avionSiege.getbyId(rs.getInt("id_avion_siege")), 
                    rs.getTimestamp("dateHeure"));
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
        if (res == null) {
            throw new Exception("Client null");
        }
        return res;
    }

    public void delete() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = null;

        // Use the configuration in your code
        String username = "postgres";
        String password = "postgres";
        String databaseName = "avion";

        try {
            conn = Postgres.getConnection(username, password, databaseName);
            conn.setAutoCommit(false);

            // Query to delete the depense from the database
            query = "DELETE FROM reservation WHERE id=?";
            stmt = conn.prepareStatement(query);

            // Set parameter for the prepared statement
            stmt.setInt(1, this.getId());

            // Execute the delete
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Delete failed, no rows affected.");
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
                conn.close();
            }
        }
    }
}
