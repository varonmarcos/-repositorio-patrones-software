package org.kallsonnys.security.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.security.dao.LDAPUserDAO;

public class UserCreationScript {

	public static void main(String[] args) {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(true);
			Statement stmt = connection.createStatement();

			LDAPUserDAO ldapUserDAO = new LDAPUserDAO();

			String pass1 = "12345";
			String pass2 = "56789";
			String passwordHash = null;

			UserDTO userDTO1 = new UserDTO();
			userDTO1.setEmail("varonmarcos@gmail.com");
			userDTO1.setName("Marcos");
			userDTO1.setSurname("Varon");

			passwordHash = SecurityUtils.createHash(pass1);

			userDTO1.setPassword(passwordHash);

			ldapUserDAO.createUser(userDTO1);

			stmt.executeUpdate(createUserSQL(userDTO1, "1"));

			UserDTO userDTO2 = new UserDTO();
			userDTO2.setEmail("danielsanchez@gmail.com");
			userDTO2.setName("Daniel");
			userDTO2.setSurname("Sanchez");

			passwordHash = SecurityUtils.createHash(pass2);

			userDTO2.setPassword(passwordHash);

			ldapUserDAO.createUser(userDTO2);

			stmt.executeUpdate(createUserSQL(userDTO2, "2"));

			// Perfiles
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (1, 'PRODUCTOS_CONSULTA', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (2, 'PRODUCTOS_ADMON', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (3, 'CAMPANAS', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (4, 'ORDENES_CONSULTA', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (5, 'ORDENES_ADMON', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (6, 'CLIENTES_CONSULTA', 0, 0)");
			stmt.executeUpdate("INSERT INTO Profile (id, name, status, version) VALUES (7, 'CLIENTES_ADMON', 0, 0)");

			// Opciones de Menu
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (1, 'Administrar Productos', '/pages/manageProduct.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (2, 'Consultar Productos', '/pages/getsProduct.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (3, 'Consultar Ranking Productos', '/pages/getsProductRanking.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (4, 'Consultar Ranking Categorias', '/pages/getsProductRanking.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (5, 'Administrar Campannas', '/pages/manageCampaing.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (6, 'Administrar Ordenes', '/pages/manageOrder.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (7, 'Consultar Ordenes', '/pages/getsOrder.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (8, 'Consultar Ranking Ordenes Abiertas', '/pages/getsOrderRankingOpen.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (9, 'Consultar Ranking Ordenes Cerradas', '/pages/getsOrderRankingClose.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (10, 'Actualizar status Orden', '/pages/updateStateOrder.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (11, 'Administrar Clientes', '/pages/manageClient.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (12, 'Consultar Clientes', '/pages/getClient.xhtml', 0, 0)");
			stmt.executeUpdate("INSERT INTO OptionMenu (id, options, url, status, version) VALUES (13, 'Consultar Ranking Clientes', '/pages/getClientRanking.xhtml', 0, 0)");
			
			//Mapeo de PErfiles con Opciones de menus
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (1, 2)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (1, 3)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (1, 4)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (2, 1)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (2, 3)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (2, 4)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (3, 5)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (4, 7)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (4, 8)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (4, 9)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (4, 10)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (5, 6)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (5, 8)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (5, 9)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (5, 10)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (6, 12)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (6, 13)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (7, 11)");
			stmt.executeUpdate("INSERT INTO optionsMenu_profile (profile_id, optionsMenu_id) VALUES (7, 13)");


			// Perfiles Marcos Varon
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (1, 1)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (2, 1)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (4, 1)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (5, 1)");

			// Perfiles Daniel Sanchez
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (1, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (2, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (3, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (4, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (5, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (6, 2)");
			stmt.executeUpdate("INSERT INTO user_profile (profile_id, user_id) VALUES (7, 2)");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String createUserSQL(UserDTO userDTO, String id) {
		return "INSERT INTO User (id, version, email, name, surname, password, status) VALUES ("
				+ id
				+ ", "
				+ "0"
				+ ", '"
				+ userDTO.getEmail()
				+ "', '"
				+ userDTO.getName()
				+ "', '"
				+ userDTO.getSurname()
				+ "', '" + userDTO.getPassword() + "', " + "0)";
	}

	public static Connection getConnection() {
		Connection conexion = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://localhost/kallsonnys_security";
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
