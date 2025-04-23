package org.serratec.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// CLASSE CONEXAO DAO - PROJETO FINAL
public class ConnectionFactory {															// classe de conexao com o banco de dados
	
	//INFORMACOES DE CONEXAO COM O BANCO DE DADOS
	private String url = "jdbc:postgresql://localhost:5432/projetofinal";		 			// url do banco de dados
	private String username = "postgres";	 												// usuario do banco de dados
	private String password = "123456";	 													// senha do banco de dados

	//GETCONNECTION OBTEM O OBJETO DE CONEXAO
	public Connection getConnection() {														// metodo para estabelecer conexao
		System.out.println("Estabelecendo conexao com o banco de dados.....");
		Connection connection = null;
		
		// TRATAMENTO DE ERRO
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Conexao estabelecida.....");
		} catch (SQLException e) {															// tratamento de erro
			System.out.println("Erro de conexao.....");
			e.printStackTrace();
		}
		return connection;
		
	}
}

