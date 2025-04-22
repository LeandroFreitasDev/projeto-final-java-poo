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

}
