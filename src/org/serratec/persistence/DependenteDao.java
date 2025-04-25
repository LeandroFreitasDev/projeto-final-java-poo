package org.serratec.persistence;

// PACKAGE PROJETO FINAL SERRATEC

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.Dependente;
import org.serratec.projeto.Funcionario;

public class DependenteDao {
	private Connection connection;

	public DependenteDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Dependente dependente, int cod_Funcionario) {
		String sql = "insert into dependente"
				+ "(nome_dependente, cpf_dependente, data_nascimento, "
				+ "parentesco, cod_funcionario ) "
				+ "values (?,?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
			stmt.setString(4, dependente.getParentesco().toString());
			stmt.setInt(5, cod_Funcionario);
			System.out.println("Passou aqui ----" + dependente.toString());
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query");
			e.printStackTrace();
		}
	}

	public void atualizar(Dependente dependente) {
		String sql = "UPDATE dependente SET (setnome=?, setcpf=?, setdata_nascimento) where =?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query: " +e.getMessage());
		}
	}

	//public static void main(String[] args) {
		//Dependente D1 = new Dependente("Marcelo", "1505900000", LocalDate.of(1990, 1, 20), null); 
		//DependenteDao dao = new DependenteDao();
		
		//dao.inserir(D1, getF);
	}
//}


// é melhor abraçar um ouriço que mexer com isso - 22/04/25 Ass: Leandro!