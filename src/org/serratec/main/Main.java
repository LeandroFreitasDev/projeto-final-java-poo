package org.serratec.main;

import java.util.List;

import org.serratec.projeto.LeituraGravacaoArquivo;

public class Main {

	public static void main(String[] args) {
		LeituraGravacaoArquivo a = new LeituraGravacaoArquivo();
		List<String[]> dadosPorLinha = a.lerArquivo();
		if (!dadosPorLinha.isEmpty()) {
			a.processarArquivo(dadosPorLinha);
			a.gerarCPFDuplicados();
			a.gerarFolha();
		}
	}
}
