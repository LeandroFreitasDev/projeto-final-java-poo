package org.serratec.projeto;

import java.time.LocalDate;

public class FolhaPagamento {
	// 6- 
	
		private Integer codigo;
		private LocalDate dataPagamento;
		private Double descontoINSS;
		private Double descontoIR;
		private Double salarioLiquido;
		private Funcionario funcionario;
		
		private static Integer contador = 0;
		
		
		// Construtor
		public FolhaPagamento(Funcionario funcionario) {
			this.codigo = contador++;
			this.funcionario = funcionario;
			this.dataPagamento = LocalDate.now();
		}


		// Get e Set
		public Integer getCodigo() {
			return codigo;
		}


		public void setCodigo(Integer codigo) {
			this.codigo = codigo;
		}


		public LocalDate getDataPagamento() {
			return dataPagamento;
		}


		public void setDataPagamento(LocalDate dataPagamento) {
			this.dataPagamento = dataPagamento;
		}


		public Double getDescontoINSS() {
			return descontoINSS;
		}


		public void setDescontoINSS(Double descontoINSS) {
			this.descontoINSS = descontoINSS;
		}


		public Double getDescontoIR() {
			return descontoIR;
		}


		public void setDescontoIR(Double descontoIR) {
			this.descontoIR = descontoIR;
		}


		public Double getSalarioLiquido() {
			return salarioLiquido;
		}


		public void setSalarioLiquido(Double salarioLiquido) {
			this.salarioLiquido = salarioLiquido;
		}


		public Funcionario getFuncionario() {
			return funcionario;
		}


		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}


		public static Integer getContador() {
			return contador;
		}


		public static void setContador(Integer contador) {
			FolhaPagamento.contador = contador;
		}
		
		// calcular descontos e salarios liquidos aqui

}
