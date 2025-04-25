package org.serratec.persistence;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.Funcionario;

//PROJETOFINAL
public class FuncionarioDAO {
	private Connection connection;

	List<Funcionario> Funcionarios = new ArrayList<>();

	public FuncionarioDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Funcionario funcionario) {
		if (existeCpf(funcionario.getCpf())) {
			System.out.println("Cpf ja cadastrado" + funcionario.getCpf());
		}

		String sql = "insert into funcionario" + "(nome_funcionario, " + "cpf_funcionario, data_nascimento, "
				+ "salario_bruto) values(?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução da query");
			e.printStackTrace();
		}
	}

	public List<Funcionario> listar() {
		String sql = "select * from funcionario";
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				funcionarios.add(new Funcionario(rs.getInt("codigo_funcionario"), rs.getString("nome_funcionario"),
						rs.getString("cpf_funcionario"), rs.getDate("data_nascimento").toLocalDate(),
						rs.getDouble("salario_bruto")));
			}

		} catch (SQLException e) {
			System.err.println("Problema na execução da query");
		}
		return funcionarios;
	}

	public boolean existeCpf(String cpf) {
		String sql = "SELECT 1 FROM funcionario WHERE cpf_funcionario = ?";
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();
			return rs.next(); // Se tiver resultado, já existe
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
		Funcionario f1 = new Funcionario("Marcelo", "15050050000", LocalDate.of(1990, 5, 10), 1000., 500., 100.,
				new ArrayList<>());
		FuncionarioDAO dao = new FuncionarioDAO();
		dao.inserir(f1);
	}
}
