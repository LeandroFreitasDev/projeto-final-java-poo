package org.serratec.main;

import java.sql.SQLException;
import java.util.List;

import org.serratec.excecoes.DependenteException;
import org.serratec.projeto.LeituraGravacaoArquivo;

public class Main {

	public static void main(String[] args) throws DependenteException, SQLException { //<
		LeituraGravacaoArquivo a = new LeituraGravacaoArquivo();
		List<String[]> dadosPorLinha = a.lerArquivo();
		if (!dadosPorLinha.isEmpty()) {
			a.processarArquivo(dadosPorLinha);
			a.gerarCPFDuplicados();
			a.gerarFolha();
		}
	}
}
