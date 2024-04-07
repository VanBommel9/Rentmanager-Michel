package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@DisplayName("Test associé à la classe ClientService")
public class TestServiceClient {


    @Test
     void Testcreation () throws ServiceException, DaoException {
        //Création d'un mock de ma classe ClientDao
        ClientDao clientDao = Mockito.mock(ClientDao.class);
        ReservationService reservationService = Mockito.mock(ReservationService.class);

        // Creation d'une instance de ClientService avec le mock de ClientDao
        ClientService clientService = new ClientService(clientDao,reservationService);


        LocalDate date = LocalDate.parse("1988-03-02");
        Client client = new Client(1,
                "dibango",
                "michel",
                "micheldibango@test.com",
                date);

        // Configuration du comportement du mock pour la couche DAO: retourne 0 castré en long
        Mockito.when(clientDao.create(client)).thenReturn(0L);

        //Configuration du comportement du mock pour la couche service
        Mockito.when(clientService.create(client)).thenReturn(0L);

        //Creation d'un client
        long ClientCree = clientService.create(client);

        assertEquals(0L,ClientCree);



    }

    @Test
    void TestFindAllClient()throws ServiceException, DaoException{

        LocalDate date1 = LocalDate.parse("1988-03-02");
        Client client1 = new Client(1,
                "dibango",
                "michel",
                "micheldibango@test.com",
                date1);
        LocalDate date2 = LocalDate.parse("1998-11-02");
        Client client2 = new Client(2,
                "enga",
                "franck",
                "franckenga@test.com",
                date2);

        //Création d'un mock de ma classe ClientDao
        ClientDao clientDao = Mockito.mock(ClientDao.class);
        ReservationService reservationService = Mockito.mock(ReservationService.class);

        // Creation d'une instance de ClientService avec le mock de ClientDao
        ClientService clientService = new ClientService(clientDao,reservationService);

        //Creation de donnée de test
        ArrayList<Client> expectedListClients = new ArrayList<>();
        expectedListClients.add(client1);
        expectedListClients.add(client2);

        //Configuration du comportement du mock pour la couche DAO
        Mockito.when(clientDao.findAll()).thenReturn(expectedListClients);

        //Configuration du comportement du mock pour la couche service
        Mockito.when(clientService.findAll()).thenReturn(expectedListClients);

        // Appel de le la liste actuelle des clients
        List<Client> actualListClient = clientService.findAll();

        // Test de comparaison
        assertEquals(expectedListClients.size(),actualListClient.size());
        assertFalse(actualListClient.contains(expectedListClients));
        assertEquals(expectedListClients,actualListClient);


    }

    @Test
    void TestFindById()throws ServiceException,DaoException{


        ClientDao clientDao = Mockito.mock(ClientDao.class);
        ClientService clientService = Mockito.mock(ClientService.class);

        LocalDate date = LocalDate.parse("1988-03-02");
        Client client = new Client(1,
                "dibango",
                "michel",
                "micheldibango@test.com",
                date);

        LocalDate date2 = LocalDate.parse("1998-11-02");
        Client client2 = new Client(2,
                "enga",
                "franck",
                "engafranck@test.com",
                date2);

        Mockito.when(clientService.findById(client.getId())).thenReturn(client);

        Client client1 = clientService.findById(1);
        Client client2actual= clientService.findById(2);


        assertEquals(client,client1);
        assertNotEquals(client,client2actual);
    }

    @Test
    void TestDeleteById(){}

}
