package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReservationService {

    private ReservationDao reservationDao;

     public ReservationService(ReservationDao reservationDao){this.reservationDao = reservationDao;}




    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer une reservation
        try {
            return reservationDao.create(reservation);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public Reservation findById(int id)throws ServiceException{
        // TODO: récupérer une reservation par son id
        try {
            return reservationDao.findById(id);
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }



    public ArrayList<Reservation> findResaByClientId(int client_id) throws ServiceException {
        // TODO: récupérer une reservation par son id
        try {
            return reservationDao.findResaByClientId(client_id);
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }

    }

    public ArrayList<Reservation> findResaByVehicleId(long id) throws ServiceException {
        // TODO: récupérer une reservation par son id
        try {
            return reservationDao.findResaByVehicleId(id);
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }

    }


    public ArrayList<Reservation> findAll() throws ServiceException {
        // TODO: récupérer toutes les reservations
        try {
            return reservationDao.findAll();
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long deleteByid(int id) throws ServiceException {
        // TODO: supprimer une reservations
        try {
            return reservationDao.deleteById(id);
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long deleteAll() throws ServiceException {
        // TODO: supprimer toutes les Reservations
        try {
            return reservationDao.deleteAll();
        }catch (DaoException e){
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long deleteByVehiculeId(int id) throws ServiceException{
        try {
            return reservationDao.deleteByVehiculeId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long deleteByClientId(int id) throws ServiceException{
        try {
            return reservationDao.deleteByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

}
