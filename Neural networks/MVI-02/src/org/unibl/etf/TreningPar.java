package org.unibl.etf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TreningPar {

	public static double izlaz, ulaz, privremenaGreska1, privremenaGreska2;
	public static double deltaN1, deltaN2, deltaN3, deltaN4, deltaN5, deltaN6, deltaN7, deltaN8;

	public static void odrediUlaziIzlaz() {
		double ulaz1 = Main.n1.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[0][2]
				+ Main.n2.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[1][2];
		Main.n3.setUlaz(ulaz1);
		Main.n3.setIzlaz(racunajAktivacionuFunkciju(ulaz1));

		double ulaz2 = Main.n1.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[0][3]
				+ Main.n2.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[1][3];
		Main.n4.setUlaz(ulaz2);
		Main.n4.setIzlaz(racunajAktivacionuFunkciju(ulaz2));

		double ulaz3 = Main.n1.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[0][4]
				+ Main.n2.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[1][4];
		Main.n5.setUlaz(ulaz3);
		Main.n5.setIzlaz(racunajAktivacionuFunkciju(ulaz3));

		double ulaz4 = Main.n1.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[0][5]
				+ Main.n2.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[1][5];
		Main.n6.setUlaz(ulaz4);
		Main.n6.setIzlaz(racunajAktivacionuFunkciju(ulaz4));

		double ulaz5 = Main.n3.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[2][6]
				+ Main.n4.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[3][6]
				+ Main.n5.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[4][6]
				+ Main.n6.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[5][6];
		Main.n7.setUlaz(ulaz5);
		Main.n7.setIzlaz(racunajAktivacionuFunkciju(ulaz5));

		double ulaz6 = Main.n3.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[2][7]
				+ Main.n4.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[3][7]
				+ Main.n5.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[4][7]
				+ Main.n6.getIzlaz() * TezinskiFaktor.matricaTezinskihFaktora[5][7];
		Main.n8.setUlaz(ulaz6);
		Main.n8.setIzlaz(racunajAktivacionuFunkciju(ulaz6));

		// System.out.println("UGAO "+Main.n7.getIzlaz());
		// System.out.println("BRZina "+Main.n8.getIzlaz());
	}

	public static double racunajAktivacionuFunkciju(double ulaz) {
		return 1 / (1 + Math.pow(Math.E, -ulaz));
		// return ulaz;
	}

	public static double izvodAktivacioneFje(double ulaz) {
		return (1 / (1 + Math.pow(Math.E, -ulaz))) * (1 - (1 / (1 + Math.pow(Math.E, -ulaz))));
	}

	public static void racunajPrivremenuGresku() {
		privremenaGreska1 = 0.5 * (Math.pow(Main.zeljenaVrijednostIzlaza1 - Main.n7.getIzlaz(), 2));
		privremenaGreska2 = 0.5 * (Math.pow(Main.zeljenaVrijednostIzlaza2 - Main.n8.getIzlaz(), 2));
		Main.privremenaGreska = privremenaGreska1 + privremenaGreska2;
	}

	public static void racunajKumulativnuGresku() {
		Main.E1 = Main.E1 + privremenaGreska1 + privremenaGreska2;
		// Main.E2 = Main.E2 + privremenaGreska2;
	}

	public static void odrediDeltaFaktor() {
		double ukupanUlaz1 = Main.n3.getUlaz() + Main.n4.getUlaz() + Main.n5.getUlaz() + Main.n6.getUlaz();
		double ukupanUlaz2 = Main.n1.getUlaz() + Main.n2.getUlaz();

		deltaN8 = (Main.zeljenaVrijednostIzlaza2 - Main.n8.getIzlaz()) * izvodAktivacioneFje(ukupanUlaz1);
		deltaN7 = (Main.zeljenaVrijednostIzlaza1 - Main.n7.getIzlaz()) * izvodAktivacioneFje(ukupanUlaz1);

		deltaN6 = (TezinskiFaktor.matricaTezinskihFaktora[5][6] * deltaN7
				+ TezinskiFaktor.matricaTezinskihFaktora[5][7] * deltaN8) * izvodAktivacioneFje(ukupanUlaz2);
		deltaN5 = (TezinskiFaktor.matricaTezinskihFaktora[4][6] * deltaN7
				+ TezinskiFaktor.matricaTezinskihFaktora[4][7] * deltaN8) * izvodAktivacioneFje(ukupanUlaz2);
		deltaN4 = (TezinskiFaktor.matricaTezinskihFaktora[3][6] * deltaN7
				+ TezinskiFaktor.matricaTezinskihFaktora[3][7] * deltaN8) * izvodAktivacioneFje(ukupanUlaz2);
		deltaN3 = (TezinskiFaktor.matricaTezinskihFaktora[2][6] * deltaN7
				+ TezinskiFaktor.matricaTezinskihFaktora[2][7] * deltaN8) * izvodAktivacioneFje(ukupanUlaz2);
		/*
		 * deltaN2 = TezinskiFaktor.matricaTezinskihFaktora[1][2] * deltaN3 +
		 * TezinskiFaktor.matricaTezinskihFaktora[1][3] * deltaN4 +
		 * TezinskiFaktor.matricaTezinskihFaktora[1][4] * deltaN5 +
		 * TezinskiFaktor.matricaTezinskihFaktora[1][5] * deltaN6; deltaN1 =
		 * TezinskiFaktor.matricaTezinskihFaktora[0][2] * deltaN3 +
		 * TezinskiFaktor.matricaTezinskihFaktora[0][3] * deltaN4 +
		 * TezinskiFaktor.matricaTezinskihFaktora[0][4] * deltaN5 +
		 * TezinskiFaktor.matricaTezinskihFaktora[0][5] * deltaN6;
		 */
	}

	public static void citajIzFajlaTreningParove() {
		// double x1[]=new double[9];
		// ArrayList<Double> x1 = new ArrayList<Double>();
		try {
			FileReader fr = new FileReader("src" + File.separator + "treningParovi.txt");
			BufferedReader br = new BufferedReader(fr);
			String linija = "";
			while ((linija = br.readLine()) != null) {
				String[] split1 = linija.split(",");
				String[] split2 = split1[1].split("-");
				String[] split3 = split2[1].split(",");

				Main.x1.add(Double.parseDouble(split1[0]));
				Main.x2.add(Double.parseDouble(split2[0]));
				Main.alfa.add(Double.parseDouble(split3[0]));
				Main.v.add(Double.parseDouble(split1[2]));
				// System.out.println(linija);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static double racunajY(double x, double v, double alfa) {

		/*
		 * double rezultat = 0; rezultat = x * Math.tan(alfa) - ((9.81 / 2) *
		 * ((Math.pow(x, 2)) / ((Math.pow(v, 2)) * Math.pow(Math.cos(alfa), 2))));
		 * return rezultat;
		 */
		return x * Math.tan(alfa) - 9.81 / 2 * Math.pow(x, 2) / (Math.pow(v, 2) * Math.pow(Math.cos(alfa), 2));
	}

}
