package org.serratec.persistence;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.conexao.ConnectionFactory;
import org.serratec.excecoes.DependenteException;
import org.serratec.projeto.Dependente;
import org.serratec.projeto.Parentesco;

public class DependenteDao {
	private Connection connection;
	private List<Dependente> dependentes = new ArrayList<>();

	public DependenteDao() {
		connection = new ConnectionFactory().getConnection();
		listar();
	}

	public void listar() {
		String sql = "SELECT * FROM dependente";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dependentes.add(new Dependente(
					rs.getString("nome_dependente"),
					rs.getString("cpf_dependente"),
					rs.getDate("data_nascimento").toLocalDate(),
					Parentesco.valueOf(rs.getString("parentesco")),
					rs.getInt("codigo_dependente")
				));
			}
		} catch (SQLException e) {
			System.err.println("Problema na execução da query para listar o dependente: " + e.getMessage());
		}
	}

	public void inserir(Dependente dependente, int cod_Funcionario) throws DependenteException {
		for (Dependente dep : dependentes) {
			if (dep.getCpf().equals(dependente.getCpf())) {
				throw new DependenteException("Dependente " + dependente.getNome() + " CPF já cadastrado.");
			}
		}

		LocalDate hoje = LocalDate.now();
		if (dependente.getDataNascimento().plusYears(18).isBefore(hoje)) {
			throw new DependenteException("Dependente " + dependente.getNome() + " é maior de 18 anos.");
		}

		String sql = "INSERT INTO dependente(nome_dependente, cpf_dependente, data_nascimento, parentesco, cod_funcionario) VALUES (?,?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
			stmt.setString(4, dependente.getParentesco().toString());
			stmt.setInt(5, cod_Funcionario);
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query para inserir o dependente: " + e.getMessage());
		}
	}

	public void atualizar(Dependente dependente) {
		String sql = "UPDATE dependente SET nome_dependente = ?, cpf_dependente = ?, data_nascimento = ?, parentesco = ? WHERE codigo_dependente = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
			stmt.setString(4, dependente.getParentesco().toString());
			stmt.setInt(5, dependente.getCodigo_dependente());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar dependente: " + e.getMessage());
		}
	}
}
