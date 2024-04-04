package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	public ReservationDao() {
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String DELETE_RESERVATIONS_QUERY = "DELETE * FROM Reservation;";
	private static final String DELETE_RESERVATIONS_QUERY_VEHICLE = "DELETE FROM Reservation WHERE vehicle_id=?;";
	private static final String DELETE_RESERVATIONS_QUERY_CLIENT = "DELETE FROM Reservation WHERE client_id=?;";


	private static final String FIND_RESERVATION_QUERY ="SELECT Reservation.id as id, Reservation.client_id as client_id, Reservation.vehicle_id as vehicle_id," +
			"Client.nom as nom,Client.prenom, Client.email, Client.naissance," +
			"Vehicle.constructeur as constructeur,Vehicle.modele,Vehicle.nb_places," +
			"debut, fin FROM Reservation " +
			"INNER JOIN Client on (Reservation.client_id = Client.id)" +
			"INNER JOIN Vehicle on (Reservation.vehicle_id = Vehicle.id) WHERE Reservation.id = ?;";

	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT Reservation.id as id, Reservation.client_id as client_id, Reservation.vehicle_id as vehicle_id," +
			"Client.nom as nom,Client.prenom, Client.email, Client.naissance," +
			"Vehicle.constructeur as constructeur,Vehicle.modele,Vehicle.nb_places," +
			"debut, fin FROM Reservation " +
			"INNER JOIN Client on (Reservation.client_id = Client.id)" +
			"INNER JOIN Vehicle on (Reservation.vehicle_id = Vehicle.id) WHERE client_id=?;";


	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT Reservation.id as id, Reservation.client_id as client_id, Reservation.vehicle_id as vehicle_id," +
			"Client.nom as nom,Client.prenom, Client.email, Client.naissance," +
			"Vehicle.constructeur as constructeur,Vehicle.modele,Vehicle.nb_places," +
			"debut, fin FROM Reservation " +
			"INNER JOIN Client on (Reservation.client_id = Client.id)" +
			"INNER JOIN Vehicle on (Reservation.vehicle_id = Vehicle.id) WHERE vehicle_id = ?;";


	private static final String FIND_RESERVATIONS_QUERY = "SELECT Reservation.id as id, Reservation.client_id as client_id, Reservation.vehicle_id as vehicle_id, " +
			"Client.nom as nom,Client.prenom, Client.email, Client.naissance," +
			"Vehicle.constructeur as constructeur,Vehicle.modele,Vehicle.nb_places," +
			"debut, fin FROM Reservation " +
			"INNER JOIN Client on (Reservation.client_id = Client.id)" +
			"INNER JOIN Vehicle on (Reservation.vehicle_id = Vehicle.id);";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1,reservation.getClient().getId());
			statement.setInt(2,reservation.getVehicule().getId());
			statement.setDate(3,Date.valueOf(reservation.getDebut()));
			statement.setDate(4,Date.valueOf(reservation.getFin()));
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
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(DELETE_RESERVATION_QUERY);
			statement.setInt(1, id);
			statement.executeUpdate();
			return (statement.executeUpdate());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	
	public ArrayList<Reservation> findResaByClientId(int client_id) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {


			PreparedStatement statement =
					connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			statement.setInt(1, (int) client_id);
			ResultSet res = statement.executeQuery();


			ArrayList<Reservation> reservations = new ArrayList<Reservation>();

			while (res.next()) {
				Reservation reservation = new Reservation();


				Client client = new Client();
				client.setId(res.getInt("client_id"));
				client.setNom(res.getString("nom"));
				client.setPrenom(res.getString("Client.prenom"));
				client.setEmail(res.getString("Client.email"));
				client.setNaissance(res.getDate("Client.naissance").toLocalDate());


				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(res.getString("constructeur"));
				vehicle.setModele(res.getString("Vehicle.modele"));
				vehicle.setNb_places(res.getInt("Vehicle.nb_places"));
				vehicle.setId(res.getInt("vehicle_id"));




				reservation.setId(res.getInt("id"));
				reservation.setClient(client);
				reservation.setVehicule(vehicle);
				reservation.setDebut(res.getDate("debut").toLocalDate());
				reservation.setFin(res.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			statement.execute();

			return reservations;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	public ArrayList<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {


			PreparedStatement statement =
					connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			statement.setInt(1, (int) vehicleId);
			ResultSet res = statement.executeQuery();


			ArrayList<Reservation> reservations = new ArrayList<Reservation>();

			while (res.next()) {
				Reservation reservation = new Reservation();


				Client client = new Client();
				client.setId(res.getInt("client_id"));
				client.setNom(res.getString("nom"));
				client.setPrenom(res.getString("Client.prenom"));
				client.setEmail(res.getString("Client.email"));
				client.setNaissance(res.getDate("Client.naissance").toLocalDate());


				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(res.getString("constructeur"));
				vehicle.setModele(res.getString("Vehicle.modele"));
				vehicle.setNb_places(res.getInt("Vehicle.nb_places"));
				vehicle.setId(res.getInt("vehicle_id"));




				reservation.setId(res.getInt("id"));
				reservation.setClient(client);
				reservation.setVehicule(vehicle);
				reservation.setDebut(res.getDate("debut").toLocalDate());
				reservation.setFin(res.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			statement.execute();

			return reservations;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Reservation findById(int id)throws DaoException{
		try(Connection connection = ConnectionManager.getConnection()) {

			PreparedStatement statement =
					connection.prepareStatement(FIND_RESERVATION_QUERY);
			statement.setInt(1, id);

			ResultSet res = statement.executeQuery();

				res.next();
				Reservation reservation = new Reservation();


				Client client = new Client();
				client.setId(res.getInt("client_id"));
				client.setNom(res.getString("nom"));
				client.setPrenom(res.getString("Client.prenom"));
				client.setEmail(res.getString("Client.email"));
				client.setNaissance(res.getDate("Client.naissance").toLocalDate());


				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(res.getString("constructeur"));
				vehicle.setModele(res.getString("Vehicle.modele"));
				vehicle.setNb_places(res.getInt("Vehicle.nb_places"));
				vehicle.setId(res.getInt("vehicle_id"));


				reservation.setId(res.getInt("id"));
				reservation.setClient(client);
				reservation.setVehicule(vehicle);
				reservation.setDebut(res.getDate("debut").toLocalDate());
				reservation.setFin(res.getDate("fin").toLocalDate());


			statement.execute();

			return reservation;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public ArrayList<Reservation> findAll() throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {


			PreparedStatement statement =
					connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet res = statement.executeQuery();


			ArrayList<Reservation> reservations = new ArrayList<Reservation>();

			while (res.next()) {
				Reservation reservation = new Reservation();


				Client client = new Client();
				client.setId(res.getInt("client_id"));
				client.setNom(res.getString("nom"));
				client.setPrenom(res.getString("Client.prenom"));
				client.setEmail(res.getString("Client.email"));
				client.setNaissance(res.getDate("Client.naissance").toLocalDate());


				Vehicle vehicle = new Vehicle();
				vehicle.setConstructeur(res.getString("constructeur"));
				vehicle.setModele(res.getString("Vehicle.modele"));
				vehicle.setNb_places(res.getInt("Vehicle.nb_places"));
				vehicle.setId(res.getInt("vehicle_id"));




				reservation.setId(res.getInt("id"));
				reservation.setClient(client);
				reservation.setVehicule(vehicle);
				reservation.setDebut(res.getDate("debut").toLocalDate());
				reservation.setFin(res.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}

			statement.execute();

			return reservations;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public long deleteAll() throws DaoException {
		try  (Connection connection = ConnectionManager.getConnection()){
			PreparedStatement statement =
					connection.prepareStatement(DELETE_RESERVATIONS_QUERY);


			return statement.executeUpdate() == 0 ? 0 : 1;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public long deleteByVehiculeId(int id) throws DaoException {
		try  (Connection connection = ConnectionManager.getConnection()){
			PreparedStatement statement =
					connection.prepareStatement(DELETE_RESERVATIONS_QUERY_VEHICLE);
			statement.setInt(1,id);


			return statement.executeUpdate() == 0 ? 0 : 1;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public long deleteByClientId(int id) throws DaoException {
		try  (Connection connection = ConnectionManager.getConnection()){
			PreparedStatement statement =
					connection.prepareStatement(DELETE_RESERVATIONS_QUERY_CLIENT);
			statement.setInt(1,id);


			return statement.executeUpdate() == 0 ? 0 : 1;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
}
