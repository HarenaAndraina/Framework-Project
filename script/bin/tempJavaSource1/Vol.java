package com.controller;

import java.util.List;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapping;
import org.framework.view.ModelView;
import org.framework.annotation.Param;
import org.framework.annotation.Post;

import com.model.AvionModel;
import com.model.VilleModel;
import com.model.VolModel;

@Controller
public class Vol {

    @RequestMapping("/vol.get")
    public ModelView getAll() {
        ModelView model = new ModelView("list-vol.jsp");

        try {
            VolModel vol = new VolModel();
            List<VolModel> volList = vol.getAll();
            VolModel[] vols = volList.toArray(new VolModel[volList.size()]);

            model.addObject("vols", vols);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping("/vol.create")
    public ModelView create() {
        ModelView model = new ModelView("form-vol.jsp");
        try {
            AvionModel avion=new AvionModel();
            VilleModel ville=new VilleModel();

            List<AvionModel> avionList=avion.getAll();
            List<VilleModel> villeList=ville.getAll();

            AvionModel[] avions=avionList.toArray(new AvionModel[avionList.size()]);
            VilleModel[] villes =villeList.toArray(new VilleModel[villeList.size()] );

            model.addObject("avions",avions);
            model.addObject("villes",villes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;

    }

    @Post
    @RequestMapping("/vol.save")
    public void save(@Param("vol") VolModel vol) {
        try {
            vol.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}