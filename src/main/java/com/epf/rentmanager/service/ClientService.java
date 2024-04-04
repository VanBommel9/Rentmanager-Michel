package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.Locale;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ReservationService reservationService;
	
	public ClientService(ClientDao clientDao, ReservationService reservationService) {
		this.clientDao = clientDao;
		this.reservationService = reservationService;
	}


	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		try {
			client.setNom(client.getNom().toUpperCase(Locale.ROOT));
			return clientDao.create(client);
		}catch (Exception e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}



	public Client findById(int id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	public ArrayList<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long deleteByid(int id) throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			try {
				reservationService.deleteByClientId(id);
			}catch (ServiceException e){
				e.printStackTrace();
			}
			return clientDao.deleteById(id);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public static boolean ifExists(Client client) throws ServiceException{
		try {
			return ClientDao.ifExists(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long deleteAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {

			return clientDao.deleteAll();
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long updateClient(Client client) throws ServiceException {
		// TODO: Editer un client
		try {
			client.setNom(client.getNom().toUpperCase(Locale.ROOT));
			return clientDao.updateById(client);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
