package com.controller;

import java.util.List;

import org.framework.annotation.Controller;
import org.framework.annotation.Param;
import org.framework.annotation.RequestMapping;
import org.framework.annotation.security.IsGranted;
import org.framework.session.CustomSession;
import org.framework.view.ModelView;
import org.framework.view.RedirectView;
import org.framework.annotation.Post;

import com.model.*;

@Controller
public class Reservation {

    @RequestMapping("/res.get")
    public ModelView selectAll() {
        ModelView model = new ModelView("list-reservation.jsp");

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

    @Post
    @RequestMapping("/res.filter")
    public ModelView filtre(@Param("avion") String avion0, @Param("dateHeureVol") String date,
            @Param("depart") String depart, @Param("arrive") String arrive) {
        ModelView model = new ModelView("list-reservation.jsp");

        try {
            VolModel vol = new VolModel();

            List<VolModel> volList = vol.rechercherVols(Integer.parseInt(avion0), convertStringToTimestamp(date),
                    Integer.parseInt(depart), Integer.parseInt(arrive));
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

    private java.sql.Timestamp convertStringToTimestamp(String value) throws Exception {
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
    

    @RequestMapping("/res.details")
    public ModelView details(@Param("id") String id_vol) {
        ModelView model = new ModelView("list-reservation-details.jsp");
        try {
            model.addObject("id_vol", id_vol);

            VolModel vol1 = new VolModel();
            vol1 = vol1.getbyId(Integer.parseInt(id_vol));

            AvionSiegeModel avionSiege = new AvionSiegeModel();
            PromotionVolModel promotionVol = new PromotionVolModel();

            List<AvionSiegeModel> avionSiegeList = avionSiege.getByIdAvion(vol1.getAvion().getId());
            List<PromotionVolModel> promotionVolList = promotionVol.getByIdVol(Integer.parseInt(id_vol));
            for (PromotionVolModel promotionVolModel : promotionVolList) {
                for (AvionSiegeModel avionSiege1 : avionSiegeList) {
                    if (promotionVolModel.getAvionSiege().getId()== avionSiege1.getId()) {
                        avionSiege1.setPromotion(avionSiege1.getPrix()
                                - (avionSiege1.getPrix() * promotionVolModel.getPourcentage() / 100));
                    } else {
                        avionSiege1.setPromotion(null);
                    }
                }
            }

            AvionSiegeModel[] avionSieges = avionSiegeList.toArray(new AvionSiegeModel[avionSiegeList.size()]);

            model.addObject("avionSieges", avionSieges);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @Post
    @RequestMapping("/res.save")
    public ModelView save(@Param("res") ReservationModel reserv) {
        ModelView model = new ModelView("message.jsp");

        try {
            DReservationModel dernierRes = new DReservationModel();
            dernierRes = dernierRes.getLastId(reserv.getVol().getId());
            if (dernierRes.getDateHeure().before(reserv.getDateHeureReserv())) {
                model.addObject("failed", "le reservation de ce vol est deja fermé!");
            } else {
                reserv.insert();
                model.addObject("success", "reservation reussi!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @IsGranted("user")
    @RequestMapping("/res.own")
    public ModelView OwnRes(CustomSession session) {
        ModelView model = new ModelView("list-own-reservation.jsp");

        try {
            int iduser = (int) session.get("user");

            ReservationModel res = new ReservationModel();
            List<ReservationModel> reservationList = res.getByUser(iduser);

            ReservationModel[] reservations = reservationList.toArray(new ReservationModel[reservationList.size()]);

            model.addObject("reservations", reservations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping("/res.annulation")
    public ModelView annulation(@Param("id") String res) {
        ModelView model = new ModelView("message.jsp");

        try {
            int resId = Integer.parseInt(res);
            ReservationModel reserv = new ReservationModel().getbyId(resId);

            if (reserv == null) {
                model.addObject("failed", "Réservation introuvable !");
                return model;
            }

            DAReservatioModel dernierAnnulation = new DAReservatioModel();

            if (reserv.getDateHeureReserv().before(dernierAnnulation.getDateHeure())) {
                model.addObject("failed", "L'annulation de reservation est déjà fermée !");
            } else {
                reserv.delete();
                model.addObject("success", "Annulation réussie !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

}
