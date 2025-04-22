package org.serratec.projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.excecoes.DependenteException;



public class Funcionario extends Pessoa{
	// 5 - Cria Classe Filha Herda do Pai e Instancia Dependentes
	
		private Double salarioBruto;
		private Double descontoInss;
		private Double descontoIR;
		
		private List<Dependente> dependentes = new ArrayList<>();

		// Construtor
		
		public Funcionario(String nome, String cpf, LocalDate dataNascimento, Double salarioBruto, Double descontoInss,
				Double descontoIR, List<Dependente> dependentes) {
			super(nome, cpf, dataNascimento);
			this.salarioBruto = salarioBruto;
			this.descontoInss = descontoInss;
			this.descontoIR = descontoIR;
			this.dependentes = dependentes;
		}
		
		// Get e Set

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
		
		// metodo Expecption controle de idade
		
		public void adicionarDependente(Dependente dependente) throws DependenteException { // dependente invalido sera trato no main
			LocalDate hoje = LocalDate.now();
			
			// criei uma classe para passar a menssagem de erro
			if(dependente.getDataNascimento().plusYears(18).isBefore(hoje)) {
				throw new DependenteException("Dependente " + dependente.getNome() + " é maior de 18 anos.");
			}
			
			// metodo Expecption CPF duplicado
			
			for (Dependente d : dependentes) {
				if(d.getCpf().equals(dependente.getCpf())) {
					throw new DependenteException("Dependente com CPF duplicado: " + dependente.getCpf());
				}
				
			}
			
			// metodo adicionar dependente
				dependentes.add(dependente);
		}

}
