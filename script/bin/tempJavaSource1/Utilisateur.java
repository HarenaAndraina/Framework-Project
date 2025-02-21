package com.controller;

import org.framework.checker.Validator;
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

@Controller
public class Utilisateur {
    @RequestMapping("/login")
    public RedirectView seeLogin() {
        return new RedirectView("login.jsp");
    }

    @Post
    @RequestMapping("/login.do")
    public RedirectView getForm(@Param("empka") UtilisateurModel user) {

        try {
            UtilisateurModel utilisateur = user.getbyPseudo();
            if (utilisateur.getRole().equals("admin")) {
                Role.add("admin");
                return new RedirectView("/vol.get");
            } else {
                return new RedirectView("/login");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/login");
        }

    }
}