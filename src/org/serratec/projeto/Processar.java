package org.serratec.projeto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.serratec.excecoes.DependenteException;
import org.serratec.persistence.DependenteDao;
import org.serratec.persistence.FuncionarioDAO;

public class Processar {
	private List<Funcionario> funcionarios = new ArrayList<>();
	private List<Funcionario> funcionariosComCpfDuplicado = new ArrayList<>();
	private Set<String> cpfsUnicos = new HashSet<>();
	private DateTimeFormatter formatar = DateTimeFormatter.ofPattern("yyyyMMdd");

	public void processarArquivo(List<String[]> dadosPorLinha) throws DependenteException, SQLException {
		List<Dependente> dependentesTemp = new ArrayList<>();
		Funcionario funcionarioAtual = null;

		for (String[] dados : dadosPorLinha) {
			boolean isFuncionario;

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

				boolean cpfExistente = false;
				for (Dependente dep : dependentesTemp) {
					if (dep.getCpf().equals(cpf)) {
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

		if (!cpfDuplicado) {
			funcionariosComCpfDuplicado.add(funcionario);
		} else {
			cpfsUnicos.add(funcionario.getCpf());
			for (Dependente d : dependentes) {
				cpfsUnicos.add(d.getCpf());
			}
			funcionarios.add(funcionario);

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.inserir(funcionario);

			DependenteDao dependenteDao = new DependenteDao();
			for (Dependente dependente : dependentes) {
				dependenteDao.inserir(dependente, funcionario.getCodigo_funcionario());
			}
		}
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<Funcionario> getFuncionariosComCpfDuplicado() {
		return funcionariosComCpfDuplicado;
	}
}
