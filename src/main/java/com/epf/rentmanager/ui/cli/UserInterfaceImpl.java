package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import java.util.ArrayList;

public class UserInterfaceImpl implements User{
    @Autowired
    ClientService clientService;

    @Autowired
    VehicleService vehicleService;


    @Override
    public void create_Client() {
        Client client = new Client();
        IOUtils.print("Création d'un nouveau client...\n");
        client.setNom(IOUtils.readString("\nNom: ",true));
        client.setPrenom(IOUtils.readString("\nPrenom: ",true));
        client.setEmail(IOUtils.readString("\nEmail: ",true));
        client.setNaissance(IOUtils.readDate("\nDate: ",true));
        try {
            clientService.create(client);
            IOUtils.print("\nUn nouveau client: \n" + client + "\n a été ajouté\n");
        }catch (ServiceException e){
            e.getMessage();
        }

    }

    @Override
    public void findById_Client(int id) {

        try {
            IOUtils.print("\nLe client recherché est: ");
            clientService.findById(id);

        }catch (ServiceException e){
           e.printStackTrace();
        }

    }

    @Override
    public void findAll_Client() {
        try {
            IOUtils.print("\nListe des clients: \n");
            IOUtils.print(clientService.findAll().toString());

        }catch (ServiceException e){
            e.getMessage();
        }

    }

    @Override
    public void deleteById_Client() {
        int id = IOUtils.readInt("\nEntrez l'id du client à supprimer: \n");
        try {

            clientService.deleteByid(id);
            IOUtils.print(clientService.findAll().toString());
        }catch (ServiceException e){
            e.getMessage();
        }

    }

    @Override
    public void deleteAll_Client() {
        try {
            IOUtils.print("\nListe des clients: \n");
            IOUtils.print(clientService.findAll().toString());

            clientService.deleteAll();
            IOUtils.print("\nListe effacée: \n");
            IOUtils.print(clientService.findAll().toString());
        }catch (ServiceException e){
            e.getMessage();
        }
    }

    @Override
    public void create_Vehicle() {
        Vehicle vehicle = new Vehicle();
        IOUtils.print("Création d'un nouveau véhicule...\n");
        vehicle.setModele(IOUtils.readString("\nModele: ",true));
        vehicle.setConstructeur(IOUtils.readString("\nConstructeur: ",true));
        vehicle.setNb_places(IOUtils.readInt("\nNombre de places: "));

        try {
            vehicleService.create(vehicle);
            IOUtils.print("\nUn nouveau client: \n" + vehicle + "\n a été ajouté\n");
        }catch (ServiceException e){
            e.getMessage();
        }
    }

    @Override
    public void findById_Vehicle() {
        int id;
        id = IOUtils.readInt("\nEntrez l'id du véhicule recherché");

        Vehicle vehicle = new Vehicle();
        try {
            IOUtils.print("\nLe client recherché est: ");
            vehicleService.findById(id);
            IOUtils.print("\n"+ vehicle);
        }catch (ServiceException e){
            e.getMessage();
        }

    }

    @Override
    public void findAll_Vehicle() {
        try {
            IOUtils.print("\nLister tous les vehicules:  \n");
            IOUtils.print(vehicleService.findAll().toString());
        }catch (ServiceException e){
            e.getMessage();
        }
    }

    @Override
    public void deleteById_Vehicle() {
        int id = IOUtils.readInt("\nEntrez l'id du client à supprimer: \n");
        try {

            vehicleService.deleteByid(id);
            vehicleService.findAll();
        }catch (ServiceException e){
            e.getMessage();
        }

    }

    @Override
    public void deleteAll_Vehicle() {
        try {
            IOUtils.print("\nSuppression de tous les véhicules: \n");
            vehicleService.deleteAll();
        }catch (ServiceException e){
            e.getMessage();
        }
    }

    @Override
    public void create_Reservation() {

    }

    @Override
    public void findById_Reservation() {

    }

    @Override
    public void findByClientId_Reservation() {

    }

    @Override
    public void findByVehicleId_Reservation() {

    }

    @Override
    public void findAll_Reservation() {

    }

    @Override
    public void deleteById_Reservation() {

    }

    @Override
    public void deleteAll_Reservation() {

    }


}
