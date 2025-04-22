package org.serratec.projeto;

public class CalcularFolha implements TabelaTaxas {
	
	static Double CalcularINSS(double salarioBruto) {
		
		//Se o salário da pessoa for menor ou igual a R$ 1.518,00 a pessoa pagará 7.5%
		if (salarioBruto <= INSS_LIMITE_7_5) {
			return salarioBruto * INSS_ALIQUOTA_7_5;
			
		//Se o salário da pessoa for menor ou igual a R$ 2.793.88 a pessoa pagará 9%
		}else if (salarioBruto <= INSS_LIMITE_9) {
			return (salarioBruto * INSS_ALIQUOTA_9) - INSS_DEDUCAO_9;
			
		//Se o salário da pessoa for menor ou igual a R$ 4.190.83 	a pessoa pagará 12%
		}else if (salarioBruto <= INSS_LIMITE_12) {
			return (salarioBruto * INSS_ALIQUOTA_12) - INSS_DEDUCAO_12;
			
		//Se o salário da pessoa for menor ou igual a R$ 8.157.41 a pessoa pagará 14%
		}else if (salarioBruto <= INSS_LIMITE_14) {
			return (salarioBruto * INSS_ALIQUOTA_14) - INSS_DEDUCAO_14;
			
		//Se o salário for acima de R$ 8.157,41 não será aplicado calculo, o desconto INSS será fixo R$ 951,62
		}else {
			return INSS_DEDUCAO_TETO;
		}
		
	}

	public static Double CalcularIR(Double salarioBruto, Double descontoINSS, Integer qtdeDependentes) {
		
		//Base para o calculo do IR
		Double baseCalculo = salarioBruto - descontoINSS - (qtdeDependentes * IR_VALOR_DEPENDENTE);
		
			//Variáveis que irão guardar os valores da alíquota e dedução
			double aliquota = 0;
			double deducao= 0;
			
			// Se a base for menor ou igual a 2.259 não será aplicado imposto.
			if (baseCalculo <= IR_LIMITE_ISENTO) {
				return 0.0;
				
			// Se a base for entre 2.259 e 2826,65 será aplicado o imposto de 7.5% - dedução
			}else if (baseCalculo <= IR_LIMITE_7_5) {
				aliquota = IR_ALIQUOTA_7_5;
				deducao = IR_DEDUCAO_7_5;
			
			// Se a base for entre 2.826,66 e 3.751,05 será aplicado o imposto de 15% - dedução
			}else if (baseCalculo <= IR_LIMITE_15) {
				aliquota = IR_ALIQUOTA_15;
				deducao = IR_DEDUCAO_15;
				
			// Se a base for entre 3.751,06 e 4.664,68 será aplicado o imposto de 22,5%	- dedução
			}else if (baseCalculo <= IR_LIMITE_22_5) {
				aliquota = IR_ALIQUOTA_22_5;
				deducao = IR_DEDUCAO_22_5;
				
			// Se a base for maior que 4.664,68, será aplicado imposto de 27,5%	 - dedução
			}else {
				aliquota = IR_ALIQUOTA_27_5;
				deducao = IR_DEDUCAO_27_5;
			}
			return (baseCalculo * aliquota) - deducao;
	}
	
	

		public static Double calcularSalarioLiquido(Double salarioBruto, Double descontoINSS, Double descontoIR) {
			return salarioBruto - descontoINSS - descontoIR;
		}
}
