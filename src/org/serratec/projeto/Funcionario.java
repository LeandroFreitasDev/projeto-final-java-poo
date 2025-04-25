package org.serratec.projeto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa {
	
	private Integer codigo_funcionario;
	private Double salarioBruto;
	private Double descontoInss;
	private Double descontoIR;

	private List<Dependente> dependentes = new ArrayList<>();

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, 
			Double salarioBruto, Double descontoInss,
			Double descontoIR, List<Dependente> dependentes) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.descontoInss = descontoInss;
		this.descontoIR = descontoIR;
		this.dependentes = dependentes;
	}

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, 
			Double salarioBruto,
			List<Dependente> dependentes) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.dependentes = dependentes;
	}

	public Funcionario(Integer codigo_funcionario, String nome, String cpf, 
			LocalDate dataNascimento, Double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.codigo_funcionario = codigo_funcionario;
		this.salarioBruto = salarioBruto;
	}

	public Integer getCodigo_funcionario() {
		return codigo_funcionario;
	}

	public void setCodigo_funcionario(Integer codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
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

	@Override
	public String toString() {
		return "Funcionario [codigo_funcionario=" + codigo_funcionario + ", salarioBruto=" + salarioBruto
				+ ", descontoInss=" + descontoInss + ", descontoIR=" + descontoIR + ", dependentes=" + dependentes
				+ "]";
	}
}
