package org.serratec.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.Funcionario;

//PROJETOFINAL
public class FuncionarioDAO {
	private Connection connection;

	public FuncionarioDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Funcionario funcionario) {
		String sql = "insert into funcionario(nome, cpf, data_nascimento) values(?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			// convertido a data
			stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento())); 
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query");
		}
	}

	public void atualizar(Funcionario funcionario) {
		String sql = "UPDATE funcionario SET nome = ?, cpf = ?, data_nascimento = ? WHERE id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento())); // TROCAR E RESOLVER CODIGO
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query");
		}
	}

	public static void main(String[] args) {
		Funcionario f1 = new Funcionario("Marcelo", "15050050000", LocalDate.of(1990, 5, 10), 1000.,500.,100., new ArrayList<>());
		FuncionarioDAO dao = new FuncionarioDAO(); 
		dao.inserir(f1);
	}
}
