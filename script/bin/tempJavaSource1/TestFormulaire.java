package com.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.framework.annotation.Controller;
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
    @RestAPI("/login.do")
    public ModelView getForm(@Param("empka")Employe emp) {
        ModelView model = new ModelView("bonjour.jsp");

        model.addObject("pseudo", emp.getPseudo());
        model.addObject("password", emp.getPassword());
        return model;
    }

    @Post
    @RequestMapping("/login.get")
    public RedirectView getForm1(@Param("empka") Employe emp,@Param("empka") Employe emp1, CustomSession sess) {

        sess.add("id", 1);
        sess.add("pseudo",emp.getPseudo());

        return new RedirectView("acceuil.jsp");
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