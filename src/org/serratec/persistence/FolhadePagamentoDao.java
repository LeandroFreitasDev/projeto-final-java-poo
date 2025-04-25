package org.serratec.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.FolhaPagamento;

public class FolhadePagamentoDao {
	private Connection connection;

	public FolhadePagamentoDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(FolhaPagamento folhaPagamento) {
		String sql = "INSERT INTO folha_de_pagamento(data_pagamento, desconto_inss, desconto_ir, salario_liquido, cod_funcionario) VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(folhaPagamento.getDataPagamento()));
			stmt.setDouble(2, folhaPagamento.getDescontoINSS());
			stmt.setDouble(3, folhaPagamento.getDescontoIR());
			stmt.setDouble(4, folhaPagamento.getSalarioLiquido());
			stmt.setInt(5, folhaPagamento.getFuncionario().getCodigo_funcionario());
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução com o banco de dados: " + e.getMessage());
		}
	}

	public void atualizar(FolhaPagamento folhaPagamento) {
		String sql = "UPDATE folha_de_pagamento SET data_pagamento = ?, desconto_inss = ?, desconto_ir = ?, salario_liquido = ?, cod_funcionario = ? WHERE codigo_pagamento = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(folhaPagamento.getDataPagamento()));
			stmt.setDouble(2, folhaPagamento.getDescontoINSS());
			stmt.setDouble(3, folhaPagamento.getDescontoIR());
			stmt.setDouble(4, folhaPagamento.getSalarioLiquido());
			stmt.setInt(5, folhaPagamento.getFuncionario().getCodigo_funcionario());
			stmt.setInt(6, folhaPagamento.getCodigo());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar folha: " + e.getMessage());
		}
	}
}
