package com.epf.rentmanager.main;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.ui.cli.UserInterfaceImpl;

public class Main {

    public static void main(String[] args) {

        UserInterfaceImpl userInterface = new UserInterfaceImpl();
        userInterface.findAll_Vehicle();

    }
}
