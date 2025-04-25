package org.serratec.main;

import java.sql.SQLException;
import java.util.List;

import org.serratec.excecoes.DependenteException;
import org.serratec.projeto.Funcionario;
import org.serratec.projeto.Gerar;
import org.serratec.projeto.LeituraArquivo;
import org.serratec.projeto.Processar;


public class Main {

	public static void main(String[] args) throws DependenteException, SQLException { 
		LeituraArquivo leitor = new LeituraArquivo();
		List<String[]> dadosPorLinha = leitor.lerArquivo();
		
		if (!dadosPorLinha.isEmpty()) {
			Processar processador = new Processar();
			processador.processarArquivo(dadosPorLinha);
			List<Funcionario> funcionarios = processador.getFuncionarios();
			List<Funcionario> funcionariosDuplicados = processador.getFuncionariosComCpfDuplicado();
			
			Gerar gerador = new Gerar();
			gerador.gerarCPFDuplicados(funcionariosDuplicados);
			gerador.gerarFolha(funcionarios);
		}
	}
}
