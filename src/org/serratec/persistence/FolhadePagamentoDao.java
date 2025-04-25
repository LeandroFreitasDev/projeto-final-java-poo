package org.serratec.persistence;
//PROJETO FINAL

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import org.serratec.conexao.ConnectionFactory;
import org.serratec.projeto.Dependente;
import org.serratec.projeto.FolhaPagamento;
import org.serratec.projeto.Funcionario;

public class FolhadePagamentoDao {
	private Connection connection;

	public FolhadePagamentoDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(FolhaPagamento folhaPagamento) {
		String sql = "INSERT INTO folha_de_pagamento(codigo_pagamento, "
				+ "data_pagamento, desconto_inss, "
				+ "desconto_ir, salario_liquido) " 
				+ "VALUES (? , ? , ? , ? , ?)";

		try {
			// verificar, pois seria ideal pegar o
			// CODIGO_FUNCIONARIO (igual ao BD)
			// funcionario

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, folhaPagamento.getCodigo());
			stmt.setDate(2, Date.valueOf(folhaPagamento.getDataPagamento()));
			stmt.setDouble(3, folhaPagamento.getDescontoINSS());
			stmt.setDouble(4, folhaPagamento.getDescontoIR());
			stmt.setDouble(5, folhaPagamento.getSalarioLiquido());
			stmt.execute();

		} catch (SQLException e) {
			System.err.println("Problema na execução com o banco de dados....." + e.getMessage());
		}
	}

	public void atualizar(FolhaPagamento folhaPagamento) {
		String sql = "UPDATE folha_de_pagamento SET data_pagamento = ? ,desconto_inss = ? , "
				+ "desconto_ir , "
				+ "salario_liquido= ?, WHERE codigo_pagamento = ?" 
				+ "VALUES (? , ? , ? , ? , ?))";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, folhaPagamento.getCodigo());
			stmt.setDate(2, Date.valueOf(folhaPagamento.getDataPagamento()));
			stmt.setDouble(3, folhaPagamento.getDescontoINSS());
			stmt.setDouble(4, folhaPagamento.getDescontoIR());
			stmt.setDouble(5, folhaPagamento.getSalarioLiquido());
			stmt.execute();
		} catch (SQLException e) {
			System.err.println("Problema na execução com o banco de dados....." + e.getMessage());
		}
	}

	public static void main(String[] args) {
		FolhaPagamento F1 = new FolhaPagamento(null);  // < resolver
		FolhadePagamentoDao dao = new FolhadePagamentoDao();

		dao.inserir(F1);
	}

}
