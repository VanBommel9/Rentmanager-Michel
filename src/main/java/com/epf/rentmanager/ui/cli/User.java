package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;

import java.util.ArrayList;

public interface User {

    public abstract void create_Client();
    public abstract void findById_Client(int id);
    public abstract void findAll_Client();
    public abstract void deleteById_Client();
    public abstract void deleteAll_Client();


    public abstract void create_Vehicle();
    public abstract void findById_Vehicle();
    public abstract void findAll_Vehicle();
    public abstract void deleteById_Vehicle();
    public abstract void deleteAll_Vehicle();


    public abstract void create_Reservation();
    public abstract void findById_Reservation();
    public abstract void findByClientId_Reservation();
    public abstract void findByVehicleId_Reservation();
    public abstract void findAll_Reservation();
    public abstract void deleteById_Reservation();
    public abstract void deleteAll_Reservation();

}
