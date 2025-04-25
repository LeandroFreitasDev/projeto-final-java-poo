package org.serratec.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private String url = "jdbc:postgresql://localhost:5432/projetofinal";
	private String username = "postgres";
	private String password = "123456";

	public Connection getConnection() {
		System.out.println("Estabelecendo conexao com o banco de dados.....");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Conexao estabelecida.....");
		} catch (SQLException e) {
			System.out.println("Erro de conexao.....");
			e.printStackTrace();
		}
		return connection;

	}
}
