package org.serratec.projeto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Gerar {

	private DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
	private DecimalFormat formatarReais = new DecimalFormat("#0.00", symbols);

	public void gerarCPFDuplicados(List<Funcionario> funcionariosComCpfDuplicado) {
		if (funcionariosComCpfDuplicado.isEmpty()) {
			System.out.println("Nenhum CPF duplicado encontrado.");
			return;
		}
		String caminhoArquivo = "./cpfs_duplicados.csv";
		try {
			FileWriter fw = new FileWriter(caminhoArquivo); 
			PrintWriter pw = new PrintWriter(fw);
			for (Funcionario f : funcionariosComCpfDuplicado) {
				pw.println("FUNCIONÁRIO: " + f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";" + f.getSalarioBruto());
				for (Dependente d : f.getDependentes()) {
					pw.println("DEPENDENTE: " + d.getNome() + ";" + d.getCpf() + ";" + d.getDataNascimento() + ";" + d.getParentesco());
				}
				pw.println();
			}
			System.out.println("Arquivo de CPFs duplicados gerado em: " + caminhoArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo de CPFs duplicados: " + e.getMessage());
		}
	}

	public void gerarFolha(List<Funcionario> funcionarios) {
		String caminhoArquivo = "./folha_pagamento.csv";
		try {
			FileWriter fw = new FileWriter(caminhoArquivo); 
			PrintWriter pw = new PrintWriter(fw);
			for (Funcionario f : funcionarios) {
				FolhaPagamento folha = new FolhaPagamento(f);
				String linha = f.getNome() + ";" +
					f.getCpf()  + ";" +
					formatarReais.format(folha.getDescontoINSS())  + ";" +
					formatarReais.format(folha.getDescontoIR())  + ";" +
					formatarReais.format(folha.getSalarioLiquido());
				pw.println(linha);
			}
			System.out.println("Folha de pagamento gerada em: " + caminhoArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo de folha de pagamento: " + e.getMessage());
		}
	}
}
