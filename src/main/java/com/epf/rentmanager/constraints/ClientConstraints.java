package com.epf.rentmanager.constraints;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import java.time.LocalDate;
import java.time.Period;

public class ClientConstraints {

    private ClientService clientService;

    public ClientConstraints(ClientService clientService) {
        this.clientService = clientService;
    }



    //private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
    public static boolean isValidAge(Client client){
        LocalDate date = LocalDate.now();
        if(Math.abs(Period.between(date,client.getNaissance()).getYears()) < 18){
            return false;
        }else {
            return true;
        }
    }

    public static boolean isValidEmail(Client client) throws ServiceException{

        try{
            return  ClientService.ifExists(client);
        }catch (ServiceException e){
            e.printStackTrace();
            System.out.println(ClientService.ifExists(client));
            System.out.println("Email existant !");
            throw new ServiceException();
        }
    }

    public static boolean isNotValidNomEtPrenom(Client client){
        boolean nom = client.getNom().length()<3;
        boolean prenom = client.getPrenom().length()<3;
        return nom || prenom;
    }


}
