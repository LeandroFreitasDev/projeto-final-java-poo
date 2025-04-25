package org.serratec.projeto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.serratec.excecoes.DependenteException;
import org.serratec.persistence.DependenteDao;
import org.serratec.persistence.FuncionarioDAO;

public class LeituraGravacaoArquivo {

	private List<Funcionario> funcionarios = new ArrayList<>();
	private List<Funcionario> funcionariosComCpfDuplicado = new ArrayList<>();
	private Set<String> cpfsUnicos = new HashSet<>();
	private DateTimeFormatter formatar = DateTimeFormatter.ofPattern("yyyyMMdd");
	private DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
	private DecimalFormat formatarReais = new DecimalFormat("#0.00", symbols);

	public List<String[]> lerArquivo() {
		List<String[]> dadosPorLinha = new ArrayList<>();

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Digite o caminho do arquivo .csv:");
			String nomeArquivo = sc.next();
			FileReader file = new FileReader(nomeArquivo);
			Scanner sc1 = new Scanner(file);

			while (sc1.hasNextLine()) {
				String linha = sc1.nextLine();
				if (!linha.isEmpty()) {
					dadosPorLinha.add(linha.split(";"));
				}
			}
			sc1.close();
		} catch (IOException e) {
			System.err.println("Arquivo não encontrado!");
		}

		return dadosPorLinha;
	}

	public void processarArquivo(List<String[]> dadosPorLinha) throws DependenteException, SQLException {
		List<Dependente> dependentesTemp = new ArrayList<>();
		Funcionario funcionarioAtual = null;

		for (String[] dados : dadosPorLinha) {
			String nome = dados[0];
			String cpf = dados[1];
			LocalDate dataNascimento = LocalDate.parse(dados[2], formatar);
			boolean isFuncionario;
			Parentesco parentesco = null;

			try {
				Double salarioBruto = Double.parseDouble(dados[3]);
				isFuncionario = true;

				if (funcionarioAtual != null) {
					processarFuncionario(funcionarioAtual, dependentesTemp);
					dependentesTemp.clear();
				}

				funcionarioAtual = new Funcionario(nome, cpf, dataNascimento, salarioBruto, new ArrayList<>());

			} catch (NumberFormatException e) {
				isFuncionario = false;
				parentesco = Parentesco.valueOf(dados[3].toUpperCase());

				boolean cpfExistente = false;
				for (Dependente dependente : dependentesTemp) {
					if (dependente.getCpf().equals(cpf)) {
						cpfExistente = true;
						break;
					}
				}

				if (!cpfExistente) {
					dependentesTemp.add(new Dependente(nome, cpf, dataNascimento, parentesco));
				}
			}
		}

		if (funcionarioAtual != null) {
			processarFuncionario(funcionarioAtual, dependentesTemp);
		}
	}

	private void processarFuncionario(Funcionario funcionario, List<Dependente> dependentes)
			throws DependenteException, SQLException {

		funcionario.setDependentes(new ArrayList<>(dependentes));

		Double salario = funcionario.getSalarioBruto();
		Double descontoInss = CalcularFolha.CalcularINSS(salario);
		Double descontoIr = CalcularFolha.CalcularIR(salario, descontoInss, dependentes.size());

		funcionario.setDescontoInss(descontoInss);
		funcionario.setDescontoIR(descontoIr);

		boolean cpfDuplicado = cpfsUnicos.contains(funcionario.getCpf());
		for (Dependente d : dependentes) {
			if (cpfsUnicos.contains(d.getCpf())) {
				cpfDuplicado = true;
				break;
			}
		}

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		funcionarioDAO.inserir(funcionario);

		DependenteDao dependenteDao = new DependenteDao();
		for (Dependente dependente : dependentes) {
			dependenteDao.inserir(dependente, funcionario.getCodigo_funcionario());
		}
		funcionario.setDependentes(new ArrayList<>(dependentes));

		if (cpfDuplicado) {
			funcionariosComCpfDuplicado.add(funcionario);
		} else {
			cpfsUnicos.add(funcionario.getCpf());
			for (Dependente d : dependentes) {
				cpfsUnicos.add(d.getCpf());
			}
			funcionarios.add(funcionario);
		}
	}

	public void gerarCPFDuplicados() {
		if (funcionariosComCpfDuplicado.isEmpty()) {
			System.out.println("Nenhum CPF duplicado encontrado.");
			return;
		}

		String caminhoArquivo = "./cpfs_duplicados.csv";

		try (FileWriter fw = new FileWriter(caminhoArquivo); PrintWriter pw = new PrintWriter(fw)) {
			for (Funcionario f : funcionariosComCpfDuplicado) {
				pw.println("FUNCIONÁRIO: " + f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";"
						+ f.getSalarioBruto());

				for (Dependente d : f.getDependentes()) {
					pw.println("DEPENDENTE: " + d.getNome() + ";" + d.getCpf() + ";" + d.getDataNascimento() + ";"
							+ d.getParentesco());
				}
				pw.println();
			}

			System.out.println("Arquivo de CPFs duplicados gerado em: " + caminhoArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo de CPFs duplicados: " + e.getMessage());
		}
	}

	public void gerarFolha() {
		String caminhoArquivo = "./folha_pagamento.csv";

		try (FileWriter fw = new FileWriter(caminhoArquivo); PrintWriter pw = new PrintWriter(fw)) {
			for (Funcionario f : funcionarios) {
				FolhaPagamento folha = new FolhaPagamento(f);

				String linha = String.join(";", f.getNome(), f.getCpf(), formatarReais.format(folha.getDescontoINSS()),
						formatarReais.format(folha.getDescontoIR()), formatarReais.format(folha.getSalarioLiquido()));

				pw.println(linha);
			}

			System.out.println("Folha de pagamento gerada em: " + caminhoArquivo);
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo de folha de pagamento: " + e.getMessage());
		}
	}
}
