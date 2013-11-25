package org.kallsonnys.security.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.security.dao.LDAPUserDAO;

public class CustomerCreationScript {

	public static void main(String[] args) {
		Connection connection = getConnection();
		try {
			System.out.println("PROCESS START");
			connection.setAutoCommit(true);
			Statement stmt = connection.createStatement();
			
			ResultSet executeQuery = stmt.executeQuery("SELECT id, password, email, name, surname FROM Customer");
			
			UserDTO userDTO = new UserDTO();
			Long id;
			String pass;
			LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
			while (executeQuery.next()) {
				id = executeQuery.getLong("id");
				pass = executeQuery.getString("password");
				userDTO.setEmail(executeQuery.getString("email"));
				userDTO.setName(executeQuery.getString("name"));
				userDTO.setSurname(executeQuery.getString("surname"));
				userDTO.setPassword(SecurityUtils.createHash(pass));
				ldapUserDAO.createUser(userDTO);
				System.out.println("UPDATE Customer SET password ='"+userDTO.getPassword()+"' WHERE id ="+id+";");
				
	        }
			System.out.println("PROCESS FINISH :)");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static Connection getConnection() {
		Connection conexion = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://localhost/kallsonnys_orders";
			String usuarioDB = "root";
			String passwordDB = "admin";
			conexion = DriverManager.getConnection(servidor, usuarioDB,
					passwordDB);
			return conexion;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conexion;
	}

}
