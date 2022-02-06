package org.unibl.etf;

import java.util.ArrayList;

public class Main {

	public static final int tezinskiFaktor = 5;
	public static final int brojIteracia = 50000;
	public static double Emax = 0.000000001, privremenaGreska = 0;
	public static double E1 = 1;
	public static double zeljenaVrijednostIzlaza1 = 0, zeljenaVrijednostIzlaza2 = 0;
	public static Neuron n1 = new Neuron(1, 1, 0, 0);
	public static Neuron n2 = new Neuron(2, 1, 0, 0);
	public static Neuron n3 = new Neuron(3, 2, 0, 0);
	public static Neuron n4 = new Neuron(4, 2, 0, 0);
	public static Neuron n5 = new Neuron(5, 2, 0, 0);
	public static Neuron n6 = new Neuron(6, 2, 0, 0);
	public static Neuron n7 = new Neuron(7, 3, 0, 0);
	public static Neuron n8 = new Neuron(8, 3, 0, 0);

	public static ArrayList<Double> x1 = new ArrayList<Double>();
	public static ArrayList<Double> x2 = new ArrayList<Double>();
	public static ArrayList<Double> alfa = new ArrayList<Double>();
	public static ArrayList<Double> v = new ArrayList<Double>();

	public static void main(String[] args) {

		TreningPar.citajIzFajlaTreningParove();

		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n1, n3);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n1, n4);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n1, n5);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n1, n6);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n2, n3);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n2, n4);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n2, n5);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n2, n6);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n3, n7);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n3, n8);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n4, n7);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n4, n8);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n5, n7);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n5, n8);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n6, n7);
		TezinskiFaktor.odredjivanjePocetnihTezinskihFaktoraIzmedjuNeurona(n6, n8);

		int i = 0, j = 0;
		while (j < brojIteracia && E1 > Emax) {

			j++;
			E1 = 0;
			System.out.println("Iteracija " + j);
			i = 0;
			System.out.println();
			while (i < 9) {

				n1.setUlaz(x1.get(i));
				n1.setIzlaz(x1.get(i) / 100);
				n2.setUlaz(x2.get(i));
				n2.setIzlaz(x2.get(i) / 100);
				zeljenaVrijednostIzlaza1 = alfa.get(i) / 100;
				zeljenaVrijednostIzlaza2 = v.get(i) / 100;
				i++;

				TreningPar.odrediUlaziIzlaz();
				TreningPar.racunajPrivremenuGresku();

				while (privremenaGreska > Emax) {
					TreningPar.racunajKumulativnuGresku();
					TreningPar.odrediDeltaFaktor();
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n1, n3);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n1, n4);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n1, n5);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n1, n6);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n2, n3);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n2, n4);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n2, n5);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n2, n6);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n3, n7);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n3, n8);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n4, n7);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n4, n8);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n5, n7);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n5, n8);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n6, n7);
					TezinskiFaktor.odredjivanjeKorigovanihTezinskihFaktora(n6, n8);

					TreningPar.odrediUlaziIzlaz();
					TreningPar.racunajPrivremenuGresku();
				}
				System.out.println("Ugao: " + n7.getIzlaz() * 100 + " Zeljeni ugao: " + zeljenaVrijednostIzlaza1 * 100);
				System.out.println(
						"Brzina: " + n8.getIzlaz() * 100 + " Zeljena brzina " + zeljenaVrijednostIzlaza2 * 100);
				System.out.println(
						"Y= " + TreningPar.racunajY(n1.getIzlaz() * 100, n8.getIzlaz() * 100, n7.getIzlaz() * 100));

			}
			System.out.println("E greska: " + E1);

		}

		/*
		 * n1.setIzlaz(17.5); n2.setIzlaz(2.5); zeljenaVrijednostIzlaza1=1.222;
		 * zeljenaVrijednostIzlaza2=11.697; TreningPar.odrediUlaziIzlaz();
		 * System.out.println("Ugao: "+n7.getIzlaz()+" Zeljeni ugao: "
		 * +zeljenaVrijednostIzlaza1);
		 * System.out.println("Brzina: "+n8.getIzlaz()+" Zeljena brzina "
		 * +zeljenaVrijednostIzlaza2);
		 * System.out.println("Y= "+TreningPar.racunajY(n1.getIzlaz()*100,
		 * n8.getIzlaz()*100, n7.getIzlaz()*100));
		 */
	}
}
