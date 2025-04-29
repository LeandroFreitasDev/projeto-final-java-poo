package org.serratec.projeto;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeituraArquivo {

	public List<String[]> lerArquivo() {
		List<String[]> dadosPorLinha = new ArrayList<>();

		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Digite o caminho do arquivo .csv:");
			String nomeArquivo = sc.next();
			FileReader file = new FileReader(nomeArquivo);
			Scanner sc1 = new Scanner(file);
			while (sc1.hasNextLine()) {
				String linha = sc1.nextLine();
				if (!linha.isEmpty()) {
					dadosPorLinha.add(linha.split(";"));
				}
			}
			System.out.println("Arquivo lido com sucesso!");
			sc1.close();
		} catch (IOException e) {
			System.err.println("Arquivo não encontrado!");
		}
		return dadosPorLinha;
	}
}
