package com.epf.rentmanager.constraints;

import com.epf.rentmanager.model.Vehicle;
import org.apache.commons.lang3.Range;

public class VehicleConstraints {



    public VehicleConstraints() {
    }

    public static boolean isNotValidVehicle(Vehicle vehicle){
        boolean modele = vehicle.getModele() == null;
        boolean constructor = vehicle.getConstructeur() == null;
        Range<Integer> range = Range.between(2, 9);
        boolean nbPlaces = range.contains(vehicle.getNb_places());

        if (modele && constructor && nbPlaces){
            return true;
        }else {
            return false;
        }
    }
}
