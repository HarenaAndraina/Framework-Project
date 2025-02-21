package com.model;

import com.database.Postgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.framework.annotation.FieldParamName;
import org.framework.annotation.validation.Min;
import org.framework.annotation.validation.Required;

public class UtilisateurModel {
    private int id;

    @Required
    @FieldParamName("pseud")
    private String pseudo;

    
    @Required
    @FieldParamName("passwrd")
    private String password;

    private String role;

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
    public UtilisateurModel(String pseudo, String password, String role) {
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
    }
    public UtilisateurModel(int id, String pseudo, String password, String role) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
    }
    public UtilisateurModel() {
    }    

    public boolean isUserValide(){
        boolean user=false;
        try {
            UtilisateurModel utilisateur= this.getbyPseudo();
            user=true;
        } catch (Exception e) {
            
        }
        return user;
    }

    public UtilisateurModel getbyPseudo() throws Exception {
        UtilisateurModel user = null;
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
            query = "SELECT * FROM utilisateur WHERE pseudo = ? and password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.getPseudo());
            stmt.setString(2, this.getPassword());

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Populate the Client object with the retrieved data
                user = new UtilisateurModel(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("password") ,
                        rs.getString("role")                       
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
        if (user == null) {
            throw new Exception("user null");
        }
        return user;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
