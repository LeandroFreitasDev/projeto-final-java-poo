package org.serratec.projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.serratec.excecoes.DependenteException;

public class Funcionario extends Pessoa {

	// FIZ ALGUMAS MODIFICACOES NO CODIGO, COMO COMENTARIOS
	// 5 - CRIAR CLASSE FILHA, HERDA DO PAI E INSTANCIA DEPENDENTES
	
	private Integer cod_funcionario;
	private Double salarioBruto; 																						
	private Double descontoInss;
	private Double descontoIR;

	private List<Dependente> dependentes = new ArrayList<>();

	// CONSTRUTOR PADRAO
	public Funcionario(String nome, String cpf, LocalDate dataNascimento, Double salarioBruto, Double descontoInss,
			Double descontoIR, List<Dependente> dependentes) {

		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.descontoInss = 0.;
		this.descontoIR = 0.;
		this.dependentes = dependentes;
//		= null;
//		cod_funcionario++;
	}

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, Double salarioBruto,
			List<Dependente> dependentes) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.dependentes = dependentes;

	}


	public Funcionario(Integer cod_funcionario, String nome, String cpf, 
			LocalDate dataNascimento,  Double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.cod_funcionario = cod_funcionario;
		this.salarioBruto = salarioBruto;
		
	}

	// Get e Set

	public  Integer getCod_funcionario() {
		return cod_funcionario;
	}

	public  void getCod_funcionario(Integer id_funcionario) {
		this.cod_funcionario = id_funcionario;
	}

	public Double getSalarioBruto() {
		return salarioBruto;

	}

	public void setSalarioBruto(Double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public Double getDescontoInss() {
		return descontoInss;
	}

	public void setDescontoInss(Double descontoInss) {
		this.descontoInss = descontoInss;
	}

	public Double getDescontoIR() {
		return descontoIR;
	}

	public void setDescontoIR(Double descontoIR) {
		this.descontoIR = descontoIR;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	// METODO EXCEPTION CONTROLE DE IDADE
	public void adicionarDependente(Dependente dependente) throws DependenteException { 					// dependente invalido sera
																											// trato no main
		LocalDate hoje = LocalDate.now();

		// CRIADO UMA CLASSE PARA PASSAR A MENSAGEM DE ERRO
		if (dependente.getDataNascimento().plusYears(18).isBefore(hoje)) {
			throw new DependenteException("Dependente " + dependente.getNome() + " é maior de 18 anos.");
		}

		// METODO PARA VERIFICAR SE O CPF DO DEPENDENTE JÁ EXISTE NA LISTA
		for (Dependente d : dependentes) {
			if (d.getCpf().equals(dependente.getCpf())) {
				throw new DependenteException("Dependente com CPF duplicado: " + dependente.getCpf());
			}

		}

		// METODO PARA ADICIONAR O DEPENDENTE
		dependentes.add(dependente);
	}

}

// BANCO DE DADOS - POSTGREE, 
// Table funcionario 
// > columns - codigo_funcionario,nome_funcionario, 
// cpf_funcionario, data_nascimento, salario_bruto 
