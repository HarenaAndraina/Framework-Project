package com.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.framework.File.FileParam;
import org.framework.annotation.Controller;
import org.framework.annotation.FileParamName;
import org.framework.annotation.Get;
import org.framework.annotation.RequestMapping;
import org.framework.annotation.RestAPI;
import org.framework.view.ModelView;
import org.framework.view.RedirectView;
import org.framework.viewScan.ViewScan;

import org.framework.annotation.Param;
import org.framework.annotation.Post;
import org.framework.session.CustomSession;
import viewsClasses.Employe;


import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

@Controller
public class TestFormulaire {
    @RequestMapping("/login")
    public RedirectView seeLogin() {
        return new RedirectView("login.jsp");
    }

    @RequestMapping("/login1")
    public RedirectView seeLogin1() {
        return new RedirectView("login1.jsp");
    }

    @Post
    @RequestMapping("/login.do")
    public ModelView getForm(@Param("empka")Employe emp,@FileParamName("file") FileParam file) {
        ModelView model = new ModelView("bonjour.jsp");

        model.addObject("pseudo", emp.getPseudo());
        model.addObject("password", emp.getPassword());
        model.addObject("file",file.getFileName("file"));

        try {
            File uploadedFile = file.getFile();
            
            // Read the content of the file
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(uploadedFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.append(line).append("\n"); // Append each line to the StringBuilder
                }
            }
    
            // Add the file content to the model
            model.addObject("fileContent", fileContent.toString());
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it, add an error message to the model, etc.)
            model.addObject("error", "File processing failed: " + e.getMessage());
        }
        return model;
    }

    @Post
    @RequestMapping("/login.get")
    public RedirectView getForm1(@Param("empka") Employe emp, CustomSession sess) {
        
         sess.add("id", 1);
        sess.add("pseudo",emp.getPseudo());

        return new RedirectView("acceuil.jsp");
         
        /*ModelView model = new ModelView("bonjour.jsp");

        model.addObject("pseudo", emp.getPseudo());
        model.addObject("password", emp.getPassword());
        return model;
        */
    }
    
    @RequestMapping("/tacheAFaire")
    public ModelView getTacheAFaire(CustomSession sess) {
        Object idEmp = sess.get("id");
        System.out.println(sess.getSessionList().size());

        ModelView model = new ModelView("tache-a-faire.jsp");

        List<Employe> emplList = Employe.AllToDo();
        List<Employe> filteredList = new ArrayList<>();

        for (Employe employe : emplList) {
            if (employe.getId()==(int) idEmp ) {
                filteredList.add(employe);
            }
        }

        Employe[] emplArray = new Employe[filteredList.size()];
        emplArray = filteredList.toArray(emplArray);

        model.addObject("tache", emplArray);
        return model;
    }

    @RequestMapping("/deconnection")
    public RedirectView deconnection(CustomSession sess){
        sess.delete("id");
        sess.delete("pseudo");
        System.out.println(sess.getSessionList().size());
        return new RedirectView("login1.jsp");
    }
}
