package br.com.live.bo;

public class Multiplicador {

	public static int ajustarQuantidade(int multiplicador, int quantidade) {

		int iValorInt = 0;
		double nValorNum = 0.000;
		double nRestoDiv = 0.000;
		int iNovaQtde = 0;
		double nQtdeAux = 0.000;
		double nMultAux = 0.000;

		nMultAux = multiplicador;
		nQtdeAux = quantidade;

		iValorInt = (int) (nQtdeAux / nMultAux);
		nValorNum = nQtdeAux / nMultAux;
		nRestoDiv = nValorNum - iValorInt;

		if (nRestoDiv >= 0.500) {
			iValorInt = iValorInt + 1;
			iNovaQtde = (int) (iValorInt * nMultAux);
		} else
			iNovaQtde = (int) (iValorInt * nMultAux);

		return iNovaQtde;
	}

}
