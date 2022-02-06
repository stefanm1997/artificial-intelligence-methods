package org.unibl.etf;

import java.util.Random;

public class TezinskiFaktor {

	public static double matricaTezinskihFaktora[][] = new double[8][8];

	public TezinskiFaktor() {
		super();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				matricaTezinskihFaktora[i][j] = 0;
			}
	}

	public static void odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(Neuron n1, Neuron n2) {

		Random rand = new Random();
		double vrijednost = rand.nextInt(6);
		matricaTezinskihFaktora[n1.getId() - 1][n2.getId() - 1] = vrijednost;
		matricaTezinskihFaktora[n2.getId() - 1][n1.getId() - 1] = vrijednost;
	}

	public static void odredjivanjeKorigovanihTezinskihFaktora(Neuron n1, Neuron n2) {
		double delta = 1;
		switch (n2.getId()) {
		case 3:
			delta = TreningPar.deltaN3;
			break;
		case 4:
			delta = TreningPar.deltaN4;
			break;
		case 5:
			delta = TreningPar.deltaN5;
			break;
		case 6:
			delta = TreningPar.deltaN6;
			break;
		case 7:
			delta = TreningPar.deltaN7;
			break;
		case 8:
			delta = TreningPar.deltaN8;
			break;
		default:
			System.out.println("Greska neka");
			break;
		}
		double promjena = Main.tezinskiFaktor * n1.getIzlaz() * delta;
		// System.out.println("PROMJENA "+promjena+" DELTA "+delta);
		matricaTezinskihFaktora[n1.getId() - 1][n2.getId() - 1] += promjena;
		matricaTezinskihFaktora[n2.getId() - 1][n1.getId() - 1] += promjena;
	}

}
