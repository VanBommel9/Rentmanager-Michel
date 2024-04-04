package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationService reservationService;

	public VehicleService(VehicleDao vehicleDao, ReservationService reservationService) {
		this.vehicleDao = vehicleDao;
		this.reservationService = reservationService;
	}


	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			if (vehicle.getConstructeur() == null ){
				throw new ServiceException();
			}else {

				return vehicleDao.create(vehicle);
			}
		}catch (Exception e){
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehicleDao.findById(id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public ArrayList<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les vehicules
		try {
			return vehicleDao.findAll();
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public long deleteByid(int id) throws ServiceException {
		// TODO: supprimer des vehicules et les reservations associées
		try {
			try {
				reservationService.deleteByVehiculeId(id);
			}catch (ServiceException e){
				e.printStackTrace();
			}
			return vehicleDao.deleteById(id);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}


	public long deleteAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.deleteAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public long updateVehicle(Vehicle vehicle) throws ServiceException {
		// TODO: Editer un vehicule
		try {

			return vehicleDao.updateById(vehicle);
		}catch (DaoException e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
