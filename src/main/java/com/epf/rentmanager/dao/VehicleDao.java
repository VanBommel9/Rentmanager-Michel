package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	public VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, modele) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String DELETE_VEHICLES_QUERY = "DELETE * FROM Vehicle ;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places, modele FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places, modele FROM Vehicle;";
	private static final String UPDATE_VEHICLES_QUERY = "UPDATE Vehicle SET constructeur = ?, nb_places = ?, modele = ?  WHERE id=?;";
	
	public long create(Vehicle vehicle) throws DaoException {
		// TODO: Créer un vehicule
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1,vehicle.getConstructeur());
			statement.setInt(2,vehicle.getNb_places());
			statement.setString(3,vehicle.getModele());

			statement.execute();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			return id;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public long deleteById(int id) throws DaoException {
		// TODO: Supprimer un vehicule par son id
		try (Connection connection = ConnectionManager.getConnection()){
			PreparedStatement statement =
					connection.prepareStatement(DELETE_VEHICLE_QUERY);
			statement.setInt(1, id);
			statement.executeUpdate();
			return statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws DaoException {
		// TODO: Rechercher un vehicule par son id
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(FIND_VEHICLE_QUERY);
			statement.setInt(1, (int) id);

			ResultSet res = statement.executeQuery();
			res.next();
			Vehicle vehicle = new Vehicle();
			vehicle.setConstructeur(res.getString("constructeur"));
			vehicle.setModele(res.getString("modele"));
			vehicle.setNb_places(res.getInt("nb_places"));
			vehicle.setId(res.getInt("id"));

			statement.execute();
			return vehicle;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public ArrayList<Vehicle> findAll() throws DaoException {
		// TODO: Afficher tous les vehicules
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet res = statement.executeQuery();

			ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

			while (res.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(res.getString("constructeur"));
				vehicle.setModele(res.getString("modele"));
				vehicle.setId(res.getInt("id"));
				vehicle.setNb_places(res.getInt("nb_places"));
				vehicles.add(vehicle);
			}

			statement.execute();
			return vehicles;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public long deleteAll() throws DaoException {
		// TODO: Supprimer tous les vehicules
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(DELETE_VEHICLES_QUERY);
			statement.executeUpdate();
			return statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public long updateById(Vehicle vehicle)throws DaoException{
		// TODO: Rechercher un vehicule paer son id
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(UPDATE_VEHICLES_QUERY);
			statement.setString(1,vehicle.getConstructeur());
			statement.setInt(2,vehicle.getNb_places());
			statement.setString(3,vehicle.getModele());
			statement.setInt(4,vehicle.getId());
			statement.executeUpdate();
			// TODO: Autre methode pour retourner la valeur du statement.executeUpdate
			/*int result = statement.executeUpdate();
			return (result);*/
			return (statement.executeUpdate());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}


}
