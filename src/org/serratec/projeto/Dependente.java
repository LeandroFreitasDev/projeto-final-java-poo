package org.serratec.projeto;

import java.time.LocalDate;

public class Dependente extends Pessoa {

	private Parentesco parentesco;
	private Integer codigo_dependente;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
	}

	public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco,
			Integer codigo_dependente) {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
		this.codigo_dependente = codigo_dependente;
	}

	public Integer getCodigo_dependente() {
		return codigo_dependente;
	}

	public void setCodigo_dependente(Integer codigo_dependente) {
		this.codigo_dependente = codigo_dependente;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	@Override
	public String toString() {
		return "Dependente [codigo_dependente=" + codigo_dependente + ", nome=" + nome + ", cpf=" + cpf
				+ ", dataNascimento=" + dataNascimento + ", parentesco=" + parentesco + "]";
	}
}
