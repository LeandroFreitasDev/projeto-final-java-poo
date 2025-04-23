package org.serratec.conexao;

import java.sql.Connection;

public class TesteFactory {
	// CLASSE DE TESTE DA CONEXAO
	public static void main(String[] args) {
		Connection connection = new ConnectionFactory().getConnection();

	}
}
