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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

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

	public void processarArquivo(List<String[]> dadosPorLinha) {
		List<Dependente> dependentesTemp = new ArrayList<>();
		Funcionario funcionarioAtual = null;
		boolean isFuncionario;

		for (String[] dados : dadosPorLinha) {
			try {
				Double.parseDouble(dados[3]);
				isFuncionario = true;
			} catch (NumberFormatException e) {
				isFuncionario = false;
			}

			if (isFuncionario) {
				if (funcionarioAtual != null) {
					processarFuncionario(funcionarioAtual, dependentesTemp);
					dependentesTemp.clear();
				}

				String nome = dados[0];
				String cpf = dados[1];
				LocalDate dataNascimento = LocalDate.parse(dados[2], formatar);
				Double salarioBruto = Double.parseDouble(dados[3]);
				funcionarioAtual = new Funcionario(nome, cpf, dataNascimento, salarioBruto, new ArrayList<>());
			} else {
				String nome = dados[0];
				String cpf = dados[1];
				LocalDate dataNascimento = LocalDate.parse(dados[2], formatar);
				Parentesco parentesco = Parentesco.valueOf(dados[3].toUpperCase());
				dependentesTemp.add(new Dependente(nome, cpf, dataNascimento, parentesco));
			}
		}

		if (funcionarioAtual != null) {
			processarFuncionario(funcionarioAtual, dependentesTemp);
			System.out.println("Passou funcionario----" + funcionarioAtual.getCod_funcionario());

		}
	}

	private void processarFuncionario(Funcionario funcionario, List<Dependente> dependentes) {
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

		FuncionarioDAO dao2 = new FuncionarioDAO();
		dao2.inserir(funcionario);

		DependenteDao dao1 = new DependenteDao();
		funcionarios = dao2.listar(); 
		for (Funcionario fun : funcionarios) {
			
		}
		for (Dependente dependente : dependentes) {
			System.out.println("passou aqui-------" + funcionario.getCod_funcionario());
			dao1.inserir(dependente, funcionario.getCod_funcionario());

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
		if (!funcionariosComCpfDuplicado.isEmpty()) {
			try {
				FileWriter fw = new FileWriter("/CPFDuplicados.csv");
				PrintWriter pw = new PrintWriter(fw);
				for (Funcionario f : funcionariosComCpfDuplicado) {
					String linha = "FUNCIONÁRIO: " + f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";"
							+ f.getSalarioBruto();
					pw.println(linha);
					for (Dependente d : f.getDependentes()) {
						String linhaDep = "DEPENDENTE: " + d.getNome() + ";" + d.getCpf() + ";" + d.getDataNascimento()
								+ ";" + d.getParentesco();
						pw.println(linhaDep);
					}
					pw.close();
				}
			} catch (IOException e) {
				System.out.println("Erro ao criar o arquivo de CPFs duplicados.");
			}
		}
	}

	public void gerarFolha() {
		try {
			FileWriter fw = new FileWriter("./folhaPagamento.csv");
			PrintWriter pw = new PrintWriter(fw);

			for (Funcionario f : funcionarios) {
				FolhaPagamento folha = new FolhaPagamento(f);

				String linha = f.getNome() + ";" + f.getCpf() + ";" + formatarReais.format(folha.getDescontoINSS())
						+ ";" + formatarReais.format(folha.getDescontoIR()) + ";"
						+ formatarReais.format(folha.getSalarioLiquido());

				pw.println(linha);
			}

			pw.close();
		} catch (IOException e) {
			System.out.println("Erro ao criar o arquivo de folha de pagamento.");
		}
	}

}
