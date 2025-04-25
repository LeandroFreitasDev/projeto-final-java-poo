package org.serratec.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.Funcionario;

public class FuncionarioDAO {
	private Connection connection;

	public FuncionarioDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Funcionario funcionario) {
		String sql = "INSERT INTO funcionario(nome_funcionario, cpf_funcionario, data_nascimento, salario_bruto) VALUES (?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				funcionario.setCodigo_funcionario(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.err.println("Problema na execução da query: " + e.getMessage());
		}
	}

	public void atualizar(Funcionario funcionario) {
		String sql = "UPDATE funcionario SET nome_funcionario = ?, cpf_funcionario = ?, data_nascimento = ?, salario_bruto = ? WHERE codigo_funcionario = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.setInt(5, funcionario.getCodigo_funcionario());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Problema na atualização: " + e.getMessage());
		}
	}

	public List<Funcionario> listar() {
		List<Funcionario> funcionarios = new ArrayList<>();
		String sql = "SELECT * FROM funcionario";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				funcionarios.add(new Funcionario(
					rs.getInt("codigo_funcionario"),
					rs.getString("nome_funcionario"),
					rs.getString("cpf_funcionario"),
					rs.getDate("data_nascimento").toLocalDate(),
					rs.getDouble("salario_bruto")
				));
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar funcionários: " + e.getMessage());
		}
		return funcionarios;
	}
}
