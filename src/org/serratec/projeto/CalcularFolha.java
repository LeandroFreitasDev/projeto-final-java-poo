package org.serratec.projeto;

public class CalcularFolha implements TabelaTaxas {

	static Double CalcularINSS(double salarioBruto) {

		if (salarioBruto <= INSS_LIMITE_7_5) {
			return salarioBruto * INSS_ALIQUOTA_7_5;

		} else if (salarioBruto <= INSS_LIMITE_9) {
			return (salarioBruto * INSS_ALIQUOTA_9) - INSS_DEDUCAO_9;

		} else if (salarioBruto <= INSS_LIMITE_12) {
			return (salarioBruto * INSS_ALIQUOTA_12) - INSS_DEDUCAO_12;

		} else if (salarioBruto <= INSS_LIMITE_14) {
			return (salarioBruto * INSS_ALIQUOTA_14) - INSS_DEDUCAO_14;

		} else {
			return INSS_CONTRIBUICAO_TETO;
		}

	}

	public static Double CalcularIR(Double salarioBruto, Double descontoINSS, Integer qtdeDependentes) {

		Double baseCalculo = salarioBruto - descontoINSS - (qtdeDependentes * IR_VALOR_DEPENDENTE);

		double aliquota = 0;
		double deducao = 0;

		if (baseCalculo <= IR_LIMITE_ISENTO) {
			return 0.0;

		} else if (baseCalculo <= IR_LIMITE_7_5) {
			aliquota = IR_ALIQUOTA_7_5;
			deducao = IR_DEDUCAO_7_5;

		} else if (baseCalculo <= IR_LIMITE_15) {
			aliquota = IR_ALIQUOTA_15;
			deducao = IR_DEDUCAO_15;

		} else if (baseCalculo <= IR_LIMITE_22_5) {
			aliquota = IR_ALIQUOTA_22_5;
			deducao = IR_DEDUCAO_22_5;

		} else {
			aliquota = IR_ALIQUOTA_27_5;
			deducao = IR_DEDUCAO_27_5;
		}

		return (baseCalculo * aliquota) - deducao;
	}

	public static Double calcularSalarioLiquido(Double salarioBruto, Double descontoINSS, Double descontoIR) {
		return salarioBruto - descontoINSS - descontoIR;
	}
}
