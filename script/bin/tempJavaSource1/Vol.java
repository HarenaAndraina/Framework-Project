package com.controller;

import java.sql.Timestamp;
import java.util.List;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapping;
import org.framework.view.ModelView;
import org.framework.view.RedirectView;
import org.framework.annotation.Param;
import org.framework.annotation.Post;
import org.framework.annotation.security.IsGranted;

import com.model.AvionModel;
import com.model.AvionSiegeModel;
import com.model.VilleModel;
import com.model.VolModel;

@IsGranted("rado")
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

            AvionModel avion = new AvionModel();
            VilleModel ville = new VilleModel();

            List<AvionModel> avionList = avion.getAll();
            List<VilleModel> villeList = ville.getAll();

            AvionModel[] avions = avionList.toArray(new AvionModel[avionList.size()]);
            VilleModel[] villes = villeList.toArray(new VilleModel[villeList.size()]);

            model.addObject("avions", avions);
            model.addObject("villes", villes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping("/vol.create")
    public ModelView createeeee() {
        ModelView model = new ModelView("form-vol.jsp");
        try {
            AvionModel avion = new AvionModel();
            VilleModel ville = new VilleModel();

            List<AvionModel> avionList = avion.getAll();
            List<VilleModel> villeList = ville.getAll();

            AvionModel[] avions = avionList.toArray(new AvionModel[avionList.size()]);
            VilleModel[] villes = villeList.toArray(new VilleModel[villeList.size()]);

            model.addObject("avions", avions);
            model.addObject("villes", villes); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @Post
    @RequestMapping("/vol.save")
    public RedirectView saveeeee(@Param("vol") VolModel vol, @Param("idVolEdit") String edit) {
        try {
            if (edit != null) {
                vol.setId(Integer.parseInt(edit));
                vol.update();
            } else {
                vol.insert();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/vol.get");
    }

    @RequestMapping("/vol.delete")
    public RedirectView deleteee(@Param("id") String id) {
        try {
            VolModel vol = new VolModel();
            vol = vol.getbyId(Integer.parseInt(id));
            vol.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/vol.get");
    }

    @RequestMapping("/vol.update")
    public ModelView update(@Param("id") String id) {
        ModelView model = new ModelView("form-vol.jsp");
        VolModel vol = new VolModel();
        try {
            model.addObject("edit", vol.getbyId(Integer.parseInt(id)));

            AvionModel avion = new AvionModel();
            VilleModel ville = new VilleModel();

            List<AvionModel> avionList = avion.getAll();
            List<VilleModel> villeList = ville.getAll();

            AvionModel[] avions = avionList.toArray(new AvionModel[avionList.size()]);
            VilleModel[] villes = villeList.toArray(new VilleModel[villeList.size()]);

            model.addObject("avions", avions);
            model.addObject("villes", villes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @Post
    @RequestMapping("/vol.filter")
    public ModelView filterrrr(@Param("avion") String avion0,@Param("dateHeureVol") String date, @Param("depart") String depart,@Param("arrive") String arrive){
        ModelView model = new ModelView("list-vol.jsp");

        try {
            VolModel vol = new VolModel();
            
            List<VolModel> volList = vol.rechercherVols(Integer.parseInt(avion0),convertStringToTime(date),Integer.parseInt(depart),Integer.parseInt(arrive));
            VolModel[] vols = volList.toArray(new VolModel[volList.size()]);

            model.addObject("vols", vols);

            AvionModel avion = new AvionModel();
            VilleModel ville = new VilleModel();

            List<AvionModel> avionList = avion.getAll();
            List<VilleModel> villeList = ville.getAll();

            AvionModel[] avions = avionList.toArray(new AvionModel[avionList.size()]);
            VilleModel[] villes = villeList.toArray(new VilleModel[villeList.size()]);

            model.addObject("avions", avions);
            model.addObject("villes", villes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    private java.sql.Timestamp convertStringToTime(String value) throws Exception {
        if (value.isEmpty()) {
            return null;
        }
        try {
            // Use the correct format matching the input: "yyyy-MM-dd'T'HH:mm"
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date parsedDate = dateFormat.parse(value);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            throw new Exception("Failed to convert value to Timestamp: " + e.getMessage(), e);
        }
        
    }

    @RequestMapping("/vol.sold")
    public ModelView sold(@Param("id_vol") String vol){
        ModelView model=new ModelView("form-sold.jsp");
        try {
            model.addObject("id_vol", vol);

            VolModel vol1=new VolModel();
            vol1=vol1.getbyId(Integer.parseInt(vol));

            AvionSiegeModel avionSiege=new AvionSiegeModel();            
            List<AvionSiegeModel> avionSiegeList=avionSiege.getByIdAvion(vol1.getAvion().getId());

            System.out.println(avionSiegeList.size()+"size avion siege");
            AvionSiegeModel[] avionSieges=avionSiegeList.toArray(new AvionSiegeModel[avionSiegeList.size()]);
            
            model.addObject("avionSieges", avionSieges);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping("/res.add")
    public ModelView reservation(@Param("id") String id){
        ModelView model=new ModelView("form-DAReservation");
        model.addObject("vol", id);
        return model;
    }

}