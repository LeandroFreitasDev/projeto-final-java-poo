package org.serratec.projeto;

import java.time.LocalDate;

public class FolhaPagamento {

	private Integer codigo_pagamento;
	private LocalDate data_pagamento;
	private Double desconto_inss;
	private Double desconto_ir;
	private Double salario_liquido;
	private Funcionario funcionario;

	public FolhaPagamento(Funcionario funcionario) {
		this.funcionario = funcionario;
		this.data_pagamento = LocalDate.now();
		this.desconto_inss = CalcularFolha.CalcularINSS(funcionario.getSalarioBruto());
		this.desconto_ir = CalcularFolha.CalcularIR(funcionario.getSalarioBruto(), this.desconto_inss,
				funcionario.getDependentes().size());
		this.salario_liquido = CalcularFolha.calcularSalarioLiquido(funcionario.getSalarioBruto(), this.desconto_inss,
				this.desconto_ir);

	}

	public Integer getCodigo() {
		return codigo_pagamento;
	}

	public void setCodigo(Integer codigo) {
		this.codigo_pagamento = codigo;
	}

	public LocalDate getDataPagamento() {
		return data_pagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.data_pagamento = dataPagamento;
	}

	public Double getDescontoINSS() {
		return desconto_inss;
	}

	public void setDescontoINSS(Double descontoINSS) {
		this.desconto_inss = descontoINSS;
	}

	public Double getDescontoIR() {
		return desconto_ir;
	}

	public void setDescontoIR(Double descontoIR) {
		this.desconto_ir = descontoIR;
	}

	public Double getSalarioLiquido() {
		return salario_liquido;
	}

	public void setSalarioLiquido(Double salarioLiquido) {
		this.salario_liquido = salarioLiquido;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
