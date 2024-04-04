package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	public ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String DELETE_CLIENTS_QUERY = "DELETE * FROM Client ;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ?  WHERE id=?;";
	private static final String FIND_CLIENT_EXIST_QUERY ="SELECT COUNT(*) FROM Client WHERE email = ?;";


	public long create(Client client) throws DaoException {
		// TODO: Créer un client
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement =
					connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1,client.getNom());
			statement.setString(2,client.getPrenom());
			statement.setString(3,client.getEmail());
			statement.setDate(4,Date.valueOf(client.getNaissance()));
			statement.execute();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			statement.close();
			connection.close();
			return id;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}

	}
	
	public long deleteById(int id) throws DaoException {
		// TODO: Supprimer un client
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(DELETE_CLIENT_QUERY);
			statement.setInt(1, id);
			statement.executeUpdate();
			return (statement.executeUpdate());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Client findById(long id) throws DaoException {
		// TODO: Rechercher un client par son id
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(FIND_CLIENT_QUERY);
			statement.setInt(1, (int) id);

			ResultSet res = statement.executeQuery();

			res.next();
			Client client = new Client();
			client.setId((int) id);
			client.setNom(res.getString("nom"));
			client.setPrenom(res.getString("prenom"));
			client.setEmail(res.getString("email"));
			client.setNaissance(res.getDate("naissance").toLocalDate());
			
			statement.execute();
			return client;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}

	public ArrayList<Client> findAll() throws DaoException {
		// TODO: Afficher la lite de clients
		try (Connection connection = ConnectionManager.getConnection()){
			PreparedStatement statement =
					connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet res = statement.executeQuery();

			ArrayList<Client> clients = new ArrayList<>();

			while (res.next()) {
				Client client = new Client();
				client.setId(res.getInt("id"));
				client.setNom(res.getString("nom"));
				client.setPrenom(res.getString("prenom"));
				client.setEmail(res.getString("email"));
				client.setNaissance(res.getDate("naissance").toLocalDate());

				clients.add(client);
			}
			statement.execute();
			return clients;
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}

	}

	public long deleteAll() throws DaoException {
		// TODO: Supprimer tous les clients
		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement statement =
					connection.prepareStatement(DELETE_CLIENTS_QUERY);
			statement.executeUpdate();

			return (statement.executeUpdate());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public long updateById(Client client)throws DaoException{
		// TODO: Modifier un client
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(UPDATE_CLIENT_QUERY);
			statement.setString(1,client.getNom());
			statement.setString(2,client.getPrenom());
			statement.setString(3,client.getEmail());
			statement.setDate(4, Date.valueOf(client.getNaissance()));
			statement.setInt(5,client.getId());
			statement.executeUpdate();

			return (statement.executeUpdate());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public static boolean ifExists(Client client)throws DaoException{
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement statement =
					connection.prepareStatement(FIND_CLIENT_EXIST_QUERY);
			statement.setString(1, client.getEmail());

			return (statement.execute());
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

}
