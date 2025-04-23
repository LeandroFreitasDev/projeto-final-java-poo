package org.serratec.projeto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeituraGravacaoArquivo {

	public LeituraGravacaoArquivo() {
		List<String[]> dadosPorLinha = new ArrayList<>();
		
		try {
			FileReader file = new FileReader("dados.csv");
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String linha = sc.nextLine();
				
				if (!linha.isEmpty()) {
					dadosPorLinha.add(linha.split(";"));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado!");
			return;
		}
		
		List<Funcionario> funcionarios = new ArrayList<>();
		List<Dependente> dependentesTemp = new ArrayList<>();
		DateTimeFormatter formatar = DateTimeFormatter.ofPattern("yyyyMMdd");
		Funcionario funcionarioAtual = null;
		
		for (String[] dados : dadosPorLinha) {
			boolean isFuncionario = false;
			
			try {
				Double.parseDouble(dados[3]);
				isFuncionario = true;
			} catch (NumberFormatException e) {
				isFuncionario = false;
				
			}
			
			if (isFuncionario) {
				if (funcionarioAtual != null) {
					funcionarioAtual.setDependentes(new ArrayList<>(dependentesTemp));
					funcionarios.add(funcionarioAtual);
					dependentesTemp.clear();
				}
				
				String nome = dados[0];
				String cpf = dados[1];
				LocalDate dataNascimento = LocalDate.parse(dados[2], formatar);
				Double salarioBruto = Double.parseDouble(dados[3]);
				funcionarioAtual = new Funcionario(nome, cpf, salarioBruto, dataNascimento, new ArrayList<>());
				
			} else {
				String nome = dados[0];
				String cpf = dados[1];
				LocalDate dataNascimento = LocalDate.parse(dados[2], formatar);
				Parentesco parentesco = Parentesco.valueOf(dados[3].toUpperCase());
				dependentesTemp.add(new Dependente(nome, cpf, dataNascimento, parentesco));
			}
		}
		
		if (funcionarioAtual != null) {
			funcionarioAtual.setDependentes(new ArrayList<>(dependentesTemp));
			funcionarios.add(funcionarioAtual);
		}
	}
}