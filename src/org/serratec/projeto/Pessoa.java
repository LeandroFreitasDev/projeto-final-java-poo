package org.serratec.projeto;

import java.time.LocalDate;

public abstract class Pessoa {
	// 2 - Criar Classe abstrata Pai Herança
	
		protected String nome;
		protected String cpf;
		protected LocalDate dataNascimento;
		
		// Construtor
		public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
			super();
			this.nome = nome;
			this.cpf = cpf;
			this.dataNascimento = dataNascimento;
		}
		
		// Get e Set

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public LocalDate getDataNascimento() {
			return dataNascimento;
		}

		public void setDataNascimento(LocalDate dataNascimento) {
			this.dataNascimento = dataNascimento;
		}

}
