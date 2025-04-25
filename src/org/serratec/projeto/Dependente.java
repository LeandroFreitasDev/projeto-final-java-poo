package org.serratec.projeto;

import java.time.LocalDate;

public class Dependente extends Pessoa{
	// 3- Criar Classe Filha Herdar do Pai
	
		private Parentesco parentesco;
		
		
		// Construtor
		public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) { 
			super(nome, cpf, dataNascimento);
			this.parentesco = parentesco;
		}

		// Get e Set
		public Parentesco getParentesco() {
			return parentesco;
		}


		public void setParentesco(Parentesco parentesco) {
			this.parentesco = parentesco;
		}

		@Override
		public String toString() {
			return "Dependente [parentesco=" + parentesco + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento="
					+ dataNascimento + ", getParentesco()=" + getParentesco() + ", getNome()=" + getNome()
					+ ", getCpf()=" + getCpf() + ", getDataNascimento()=" + getDataNascimento() + ", getClass()="
					+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
		}

		
	}

// BANCO DE DADOS - POSTGREE, Table Dependente 
// > columns - codigo_dependente, nome_dependente, 
// cpf_dependente, data_nascimento, 
// parentesco, cod_funcionario