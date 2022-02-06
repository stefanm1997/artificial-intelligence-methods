package org.unibl.etf;

import java.util.Collections;

import javax.swing.JDialog;

import javax.swing.JOptionPane;

public class Main {

	public static void prikaziRezultat(String s) { //f-ja koja prikazuje rezultat u obliku informacionog alerta
		JOptionPane optionPane = new JOptionPane(s, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
		JDialog dialog = optionPane.createDialog("Rezultat");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	public static void main(String[] args) {

		//prije pokretanja programa potrebno je u konfiguracionom fajlu podesiti sve parametre
		//program racuna minimum i maksimum f-je koji su predstavljeni sa min i maks, analogno
		//u slucaju unosenja nekih neocekivanih parametara,program se nece moci izvrsiti i bice ispisana odgovarajuca poruka
	try {
		Pomocna.izborPocetnePopulacije(); //f-ja koja generise pocetnu populaciju tacaka
		for (int j = 0; j < (int) Pomocna.brojIteracija; j++) { //prolazi se kroz sve iteracije
			System.out.println("Iteracija broj "+j+".");
			for (Tacka tacka : Pomocna.nizTacaka) { //prolazi se kroz sve tacke i racuna se odgovarajuca f-ja
				System.out.println(tacka);
				Tacka.racunajFunkcijuZ(tacka.getX(), tacka.getY());
			}

			if (Pomocna.tipFunkcije.equals("min")) { //racunanje minimuma f-je
				 //System.out.println("+++++++++++"+Tacka.listaFunkcijeZ);
				Collections.sort(Tacka.listaFunkcijeZ, Collections.reverseOrder()); //sortira se lista opadajuce
				 //System.out.println(Tacka.listaFunkcijeZ);

				System.out.println("Fitnes funkcija: " + Pomocna.fitnesFunkcijaMin(Tacka.listaFunkcijeZ)); //racunanje fitnes f-je
				System.out.println(
						"Kumulativne vjerovatnoce: " + Pomocna.kumulativnaVjerovatnocaMin(Tacka.listaFunkcijeZ)); //racunanje kumulativnih vjerovatnoca
			} else if (Pomocna.tipFunkcije.equals("maks")) { //racunanje maksimuma f-je
				// System.out.println(Tacka.listaFunkcijeZ);
				Collections.sort(Tacka.listaFunkcijeZ); //sortira se lista rastuce
				// System.out.println(Tacka.listaFunkcijeZ);

				System.out.println("Fitnes funkcija: " + Pomocna.fitnesFunkcijaMaks(Tacka.listaFunkcijeZ));  //racunanje fitnes f-je
				System.out.println(
						"Kumulativne vjerovatnoce: " + Pomocna.kumulativnaVjerovatnocaMaks(Tacka.listaFunkcijeZ)); //racunanje kumulativnih vjerovatnoca
			}
			if (j == (int) Pomocna.brojIteracija - 1) { //ovo sluzi da odredi fitnes funkciju zadnje generacije tacaka
				Tacka.fitnesFunkcijaZadnjeGeneracije.addAll(Pomocna.rezultatFitnesa);
			}

			Tacka.listaFunkcijeZ.clear(); //prazni listu funkcija
			Pomocna.rezultatFitnesa.clear(); //prazni listu fitnesa
			Pomocna.ocjenaPopulacije = 0; //restartuje ocjenu populacije

			System.out.println("Indeksi tacaka koje prolaze u sledecu generaciju: \n"
					+ Pomocna.izborSelekcije(Pomocna.listaKumulativnihVjerovatnoca)); //izbor indeksa tacaka koji prolaze u sledecu generaciju
			Pomocna.praviSledecuGeneraciju(Pomocna.listaZaSledecuGeneraciju); //pravi sledecu generaciju tacaka na osnovu prethodno odredjenih indeksa
			Pomocna.listaZaSledecuGeneraciju.clear(); //prazni listu tacaka
			Pomocna.listaKumulativnihVjerovatnoca.clear(); //prazni listu kumultavnih vjerovatnoca
			Pomocna.vjerovatnocaIzbora.clear(); //prazni listu vjerovatnoca izbora
			// System.out.println(Pomocna.listaSledeceGeneracije);
			Pomocna.mijesanjeTacaka(Pomocna.listaSledeceGeneracije); //mijesa tacke na prethodno odredjen nacin
			// System.out.println(Pomocna.listaSledeceGeneracije);
			Pomocna.listaSledeceGeneracije.clear(); //prazni listu tacaka sledece generacije 

			for (int i = 0; i < (int) Pomocna.velicinaPopulacije / 2; i++) { //vrsi rekombinaciju svih parova tacaka,uzima 2 po 2
				Tacka.rekombinacijaTacke(Pomocna.nizTacaka[i], Pomocna.nizTacaka[i + 1]);

			}

			if ((int) Pomocna.velicinaPopulacije % 2 != 0) { //ako je velicina populacije neparna ovo nam sluzi da zadnju tacku dodamo u sledecu generaciju, tj. da nam ona ne bi ostala neiskoristena
				Tacka.listaNovihTacaka.add(Pomocna.nizTacaka[(int) Pomocna.velicinaPopulacije - 1]);
			}

			// System.out.println(Tacka.listaNovihTacaka);

			Tacka.mutacija(Tacka.listaNovihTacaka); //vrsi se mutacija svih tacki
			System.out.println("Tacke koje su spremne za sledecu generaciju:\n" + Tacka.listaNoveGeneracijeTacaka);

			for (int i = 0; i < (int) Pomocna.velicinaPopulacije; i++) { 
				Pomocna.nizTacaka[i] = Tacka.listaNoveGeneracijeTacaka.get(i); //dodavanje tacaka nove generacije u pocetni niz tacaka
			}

			if (j == (int) Pomocna.brojIteracija - 1) { //odredjuje se zadnja generacija tacaka
				Tacka.zadnjaGeneracija.addAll(Tacka.listaNoveGeneracijeTacaka);
			}
			Tacka.listaNovihTacaka.clear(); //prazni listu novih tacaka
			Tacka.listaNoveGeneracijeTacaka.clear(); //prazni listu nove generacije tacaka

		}

		System.out.println("Ovo je zadnja generacija tacki: " + Tacka.zadnjaGeneracija);
		System.out.println("Ovo je fitnes funkcija zadnje generacije: " + Tacka.fitnesFunkcijaZadnjeGeneracije);
		Pomocna.ocjenaPopulacije = 0;

		//odredjivanje krajnjeg rezultata
		for (Tacka t : Tacka.zadnjaGeneracija) { //prolazak kroz zadnju generaciju tacaka i racunanje f-je
			Tacka.racunajFunkcijuZ(t.getX(), t.getY());
		}

		if ("min".equals(Pomocna.tipFunkcije)) { //ako se trazi minimum
			Collections.sort(Tacka.listaFunkcijeZ); //sortira se lista
			Double minVrijednost = Tacka.listaFunkcijeZ.get(0); //uzima se prvi element
			Tacka.listaFunkcijeZ.clear(); //prazni se lista
			System.out.println("Najmanja vrijednost funkcije je:" + minVrijednost);
			for (Tacka t : Tacka.zadnjaGeneracija) {
				Tacka.racunajFunkcijuZ(t.getX(), t.getY()); //prolazak ponovo kroz zadnju generaciju tacaka i racunanje f-je
				if (Tacka.listaFunkcijeZ.contains(minVrijednost)) { //ako lista izracunatih f-ja sadrzi tu najmanju vrijednost ispisi rezultat
					/*Tacka.listaFunkcijeZ.clear();
					Tacka.racunajFunkcijuZ(t.getX(), t.getY());*/
					prikaziRezultat(
							"Najmanja vrijednost funkcije je: " + minVrijednost + " i ostvarena je u tacki: " + t); //prikaz rezultata
					break;
				}
			}
		} else if ("maks".equals(Pomocna.tipFunkcije)) { //ako se trazi maksimum
			Collections.sort(Tacka.listaFunkcijeZ, Collections.reverseOrder()); //sortira se lista obrnutim redoslijedom
			Double maksVrijednost = Tacka.listaFunkcijeZ.get(0); //uzima se prvi element
			Tacka.listaFunkcijeZ.clear(); //prazni se lista
			System.out.println("Najveca vrijednost funkcije je:" + maksVrijednost);
			for (Tacka t : Tacka.zadnjaGeneracija) {
				Tacka.racunajFunkcijuZ(t.getX(), t.getY());//prolazak ponovo kroz zadnju generaciju tacaka i racunanje f-je
				if (Tacka.listaFunkcijeZ.contains(maksVrijednost)) {//ako lista izracunatih f-ja sadrzi tu najvecu vrijednost ispisi rezultat
					/*Tacka.listaFunkcijeZ.clear();
					Tacka.racunajFunkcijuZ(t.getX(), t.getY());*/
					prikaziRezultat(
							"Najveca vrijednost funkcije je: " + maksVrijednost + " i ostvarena je u tacki: " + t);  //prikaz rezultata
					break;
				}
			}
		}

		System.exit(0);
	}
	
	catch(Exception e) {
		System.out.println("Desilo se nesto nepredvidjeno!!!");
		}
	}

}
