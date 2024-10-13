package viewsClasses;

import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.FieldParamName;

public class Employe {
    private int id;

    @FieldParamName("pseud")
    private String pseudo;

    @FieldParamName("passwrd")
    private String password;
    
    private String tache;

    
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
    
    
    public Employe(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }
    
    public Employe(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
    }

    
    public Employe(int id, String tache) {
        this.id = id;
        this.tache = tache;
    }
    public Employe() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }    
    public String getTache() {
        return tache;
    }
    public void setTache(String tache) {
        this.tache = tache;
    }

    public static List<Employe> AllToDo(){
        Employe emp=new Employe(1,"manasa trano");
        Employe emp1=new Employe(1,"Mahandro sakafo");
        Employe emp2=new Employe(2, "mietsena");

        List<Employe> listTache=new ArrayList<>();

        listTache.add(emp2);
        listTache.add(emp);
        listTache.add(emp1);

        return listTache;
    }
    
}
