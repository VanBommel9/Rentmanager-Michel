package com.epf.rentmanager.constraints;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReservationConstraints {


    public static boolean dayReservation(Reservation reservation, int vehicle_id,ReservationService reservationService) throws ServiceException {
        try {
            ArrayList<Reservation> reservations = reservationService.findResaByVehicleId(vehicle_id);

            for (Reservation reserved : reservations) {
                boolean into = reservation.getDebut().isAfter(reserved.getDebut()) && (reservation.getFin().isBefore(reserved.getFin()));
                boolean across = reservation.getDebut().isBefore(reserved.getDebut()) && reservation.getFin().isBefore(reserved.getFin());
                boolean outside = reservation.getDebut().isBefore(reserved.getDebut()) && reservation.getFin().isAfter(reserved.getFin());
                boolean equal = reservation.getDebut().isEqual(reserved.getDebut()) && reservation.getFin().isEqual(reserved.getFin()) ;
                //boolean accross_2 = reservation.getDebut().isAfter((reserved.getDebut())) && reserved.getFin().isBefore(reservation.getFin());

                if (into || across || equal || outside){
                    System.out.println("Ce vehicule est déjà réservé durant une période à cette date");
                    return false;
                }
            }
            return true;

        }catch (ServiceException e){
            e.printStackTrace();
            System.out.println("Erreur de verification de date pour le même jour");
            throw new ServiceException();
        }
    }

    public static boolean timeReservation(Reservation reservation){
        if (ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin()) > 7){
            System.out.println("Impossible de reserver plus de 7 jours de suite !");
            return false;
        }else {
            return true;
        }
    }

    public static boolean frequencyReservation(Reservation reservation, int vehicle_id,ReservationService reservationService)throws Exception{
        try {
            ArrayList<Reservation> reservations = reservationService.findResaByVehicleId(vehicle_id);
            Collections.sort(reservations,Comparator.comparing(Reservation::getDebut));
            long reserv_en_cours = ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin());
            int i = 0 ;
            long sum =0;
            while ( i < reservations.size() && (sum <= 30 ) ){
                boolean verif = true;
                for (Reservation reserved : reservations) {
                    boolean into = reservation.getDebut().isAfter(reserved.getDebut()) && (reservation.getFin().isBefore(reserved.getFin()));
                    boolean across = reservation.getDebut().isBefore(reserved.getDebut()) && reservation.getFin().isBefore(reserved.getFin());
                    boolean outside = reservation.getDebut().isBefore(reserved.getDebut()) && reservation.getFin().isAfter(reserved.getFin());
                    boolean equal = reservation.getDebut().isEqual(reserved.getDebut()) && reservation.getFin().isEqual(reserved.getFin()) ;
                    boolean across2 = reserved.getFin().isAfter(reservation.getDebut()) && reservation.getFin().isBefore(reserved.getFin());


                    if (into || across || equal || outside || across2){
                        System.out.println("Ce vehicule est déjà réservé durant une période à cette date");
                        verif = false;
                    }
                }

                if (verif == false) {
                    sum += ChronoUnit.DAYS.between(reservations.get(i).getDebut(), reservations.get(i).getFin());
                }
                i++;
            }
            if(sum < 30){
                sum += reserv_en_cours;
                if (sum == 30){
                    System.out.println("Ce vehicule a atteint 30 jours");
                    return true;
                }else{
                    return true;
                }
            }else {
                System.out.println("Ce vehicule a depassé les 30 jours  avce la réservation que vous voulez");
                return false;
            }
        }catch (ServiceException e){
            throw new Exception("Erreur de vérification de date pour 30 jours");
        }
    }
}
