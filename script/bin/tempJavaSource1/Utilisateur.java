package com.controller;

import org.framework.checker.Validator;
import org.framework.session.CustomSession;
import org.framework.annotation.security.Role;
import org.framework.annotation.Controller;
import org.framework.annotation.Param;
import org.framework.annotation.Post;
import org.framework.annotation.RequestMapping;
import org.framework.view.ModelView;
import org.framework.view.RedirectView;
import com.model.UtilisateurModel;
import com.model.VolModel;
import java.util.List;
import org.framework.session.CustomSession;


@Controller
public class Utilisateur {
    @RequestMapping("/login")
    public RedirectView seeLogin() {
        return new RedirectView("login.jsp");
    }

    @Post
    @RequestMapping("/login.do")
    public RedirectView getForm(@Param("empka") UtilisateurModel user,CustomSession session) {

        try {
            UtilisateurModel utilisateur = user.getbyPseudo();
            if (utilisateur.getRole().equals("admin")) {
                Role.add("admin");
                return new RedirectView("/vol.get");
            } else if (utilisateur.getRole().equals("user")) {
                Role.add("user");
                session.add("user", utilisateur.getId());
                return new RedirectView("/res.get");
            } else {
                return new RedirectView("/login");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/login");
        }

    }

    @RequestMapping("/deconnection")
    public RedirectView deconnection(CustomSession sess){
        sess.delete("role");
        sess.delete("user");
        System.out.println(sess.getSessionList().size());
        return new RedirectView("/login");
    }
}