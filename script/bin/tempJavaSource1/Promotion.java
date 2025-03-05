package com.controller;

import org.framework.annotation.Controller;
import org.framework.annotation.Param;
import org.framework.annotation.Post;
import org.framework.annotation.RequestMapping;
import org.framework.annotation.security.IsGranted;
import org.framework.view.RedirectView;

import com.model.*;

@IsGranted("admin")
@Controller
public class Promotion {

    @Post
    @RequestMapping("/sold.save")
    public RedirectView insert(@Param("sold") PromotionVolModel sold ){
        try {
            sold.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/vol.get");
    }

    @RequestMapping("/d.create")
    public RedirectView create_d(){
        return new RedirectView("form-DAReservation.jsp");
    }
    
    @Post
    @RequestMapping("/d.save")
    public RedirectView insert_d(@Param("d") DReservationModel davida ){
        try {
            davida.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/vol.get");
    }

    @Post
    @RequestMapping("/da.save")
    public RedirectView insert_da(@Param("da") DAReservatioModel davida ){
        try {
            davida.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/vol.get");
    }

}
