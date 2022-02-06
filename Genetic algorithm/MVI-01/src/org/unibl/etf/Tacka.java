package org.unibl.etf;

import java.util.ArrayList;
import java.util.Random;

public class Tacka {

	private double x, y; //(x,y) kordinate tacke
	public static ArrayList<Double> listaFunkcijeZ = new ArrayList<Double>();
	public static String tacka1, tacka2;
	public static int redniBrojGenaNaKomSeVrsiUkrstanje;
	public static ArrayList<Tacka> listaNovihTacaka = new ArrayList<Tacka>();
	public static ArrayList<Tacka> listaNoveGeneracijeTacaka = new ArrayList<Tacka>();
	public static ArrayList<Tacka> zadnjaGeneracija = new ArrayList<Tacka>();
	public static ArrayList<Double> fitnesFunkcijaZadnjeGeneracije = new ArrayList<Double>();

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Tacka() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tacka(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Tacka [x=" + x + ", y=" + y + "]";
	}

	public static String konverzijaTacke(double x, double y) {  //konverzija tacke u string tako da bude predstavljena u binarnom zapisu
		String konverzijaX = Pomocna.kodovanjeBDa(Pomocna.racunajBD(x));
		String konverzijaY = Pomocna.kodovanjeBDa(Pomocna.racunajBD(y));
		return konverzijaX + konverzijaY;

	}

	public static void racunajFunkcijuZ(double x, double y) { //funkcija koja racuna zadatu funkciju za odredjenu tacku
		double rez = 3 * Math.pow(1 - x, 2) * Math.pow(Math.E, -(Math.pow(x, 2) + Math.pow(y + 1, 2)))
				- 10 * (x / 5 - Math.pow(x, 3) - Math.pow(y, 5)) * Math.pow(Math.E, -(Math.pow(x, 2) + Math.pow(y, 2)))
				- 1 / 3 * Math.pow(Math.E, -(Math.pow(x + 1, 2) + Math.pow(y, 2)));
		listaFunkcijeZ.add(rez); //vraca listu koja je tipa double

	}

	public static void ukrstanje(String a, String b, int brojNaKomSeUkrsta) { //f-ja koja vrsi ukrstanje 2 hromozoma tj. 2 tacke koje su prije toga pretvorene u binarni zapis
		if (brojNaKomSeUkrsta < (Pomocna.n * 2)) {
			StringBuilder sb1 = new StringBuilder(); //radi se prvo jedna tacka
			sb1.append(a);					//dodaje se u StringBuilder da bi se dobio obrnut zapis bita
			sb1.reverse();					//tj. da biti budu poredani po odgovarajucim tezinama kao sto je i u algoritmu 
			String aa = sb1.toString();		//druga tacka
			StringBuilder sb2 = new StringBuilder();
			sb2.append(b);
			sb2.reverse();
			String bb = sb2.toString();
			String prvi1 = String.copyValueOf(aa.toCharArray(), 0, brojNaKomSeUkrsta);	//tacke se dijele na 2 dijela, 1. dio je dio od pocetka do bita gdje se ukrsta, a drugi je od tog bita pa do kraja
			String prvi2 = String.copyValueOf(aa.toCharArray(), brojNaKomSeUkrsta, aa.length() - brojNaKomSeUkrsta);
			String drugi1 = String.copyValueOf(bb.toCharArray(), 0, brojNaKomSeUkrsta);  //isto i za drugu tacku
			String drugi2 = String.copyValueOf(bb.toCharArray(), brojNaKomSeUkrsta, bb.length() - brojNaKomSeUkrsta);

			tacka1 = drugi1 + prvi2; //ovdje se pravi nova tacka, tj. ta ukrstena tacka gdje se samo saberu unakrsno dijelovi koje smo prije toga dobili rastavljanjem tacke na 2 dijela
			tacka2 = prvi1 + drugi2; //isto i za drugu
		}
	}

	public static void rekombinacijaTacke(Tacka a, Tacka b) { //f-ja koja vrsi rekombinaciju 2 tacke
		String prva = konverzijaTacke(a.x, a.y);  //pretvara tacku u string,objasnjeno je prethodno
		String druga = konverzijaTacke(b.x, b.y);

		Random randTackeRekombinacije = new Random(); //2 random vrijednosti koje nam sluze da se odredi na kom mjestu tj bitu se vrsi ukrstanje
		Random randVjerovatnoce = new Random();		//i koja ce biti vrijednost vjerovatnoce ukrstanja tj. da li ce doci do rekombinacije ili nece

		if (randVjerovatnoce.nextDouble() < Pomocna.vjerovatnocaUkrstanja) {
			redniBrojGenaNaKomSeVrsiUkrstanje = randTackeRekombinacije.nextInt((int) Pomocna.n * 2); //odredjivanje slucajnog broja na osnovu broja bita od kojih se sastoji tacka
			ukrstanje(prva, druga, redniBrojGenaNaKomSeVrsiUkrstanje); //poziv f-je ukrstanje sa odgovarajucim parametrima koja ce izvrsiti vec objasnjeno ukrstanje
			konverziijaTackeUDecimalniZapis(tacka1, tacka2);	//i na kraju pretvaranje ukrstene tacke u decimalni oblik, tj. konverzija ukrstene tacke u njen osnovni oblik sa 2 kordinate (x,y) koje su decimalnog tipa
		} else {
			konverziijaTackeUDecimalniZapis(prva, druga);
		}

	}

	public static ArrayList<Tacka> konverziijaTackeUDecimalniZapis(String prva, String druga) { //f-ja koja pretvarana tacku iz stringa koji sadrzi binarnu reprezentaciju tacke u decimalni zapis te tacke

		String prvi1 = String.copyValueOf(prva.toCharArray(), 0, Pomocna.n); //dijelimo tacku na 2 dijela
		String prvi2 = String.copyValueOf(prva.toCharArray(), Pomocna.n, (Pomocna.n * 2) - Pomocna.n); //u ovom slucaju se dijeli na 2 jednaka dijela tj. na 2 jednake polovine da bi mogli dobiti obe kordinate pojedinacno 
		String drugi1 = String.copyValueOf(druga.toCharArray(), 0, Pomocna.n);
		String drugi2 = String.copyValueOf(druga.toCharArray(), Pomocna.n, (Pomocna.n * 2) - Pomocna.n);

		int x1 = Integer.parseInt(prvi1, 2); //konvertujem taj 1 dio u int ,tj. prvu kordinatu konvertujemo u bd
		int y1 = Integer.parseInt(prvi2, 2); //isto i za sve ostale kordinate
		int x2 = Integer.parseInt(drugi1, 2);
		int y2 = Integer.parseInt(drugi2, 2);
		listaNovihTacaka.add(new Tacka(Pomocna.konverzijaBDuX(x1), Pomocna.konverzijaBDuX(y1))); //kao rezultat vracamo listu tacaka koje su dobijene kreiranjem novih tacaka
		listaNovihTacaka.add(new Tacka(Pomocna.konverzijaBDuX(x2), Pomocna.konverzijaBDuX(y2))); //nove tacke su dobijene konverzijom prethodnog dobijenog bd-a u x preko pomocne f-je 

		return listaNovihTacaka;  //vraca se ta lista
	}

	public static void mutacija(ArrayList<Tacka> lista) {  //f-ja koja vrsi mutaciju tacke u odredjenom bitu
		Random randVjerovatnoce = new Random();  //2 random vrijednosti koje sluze za odredjivanje bita gdje se vrsi mutacija
		Random randTackeMutacije = new Random(); //i vrijednost vjerovatnoce mutacije tj. da li ce doci do mutacije ili nece
		for (Tacka t : lista) {
			String konverzija = konverzijaTacke(t.getX(), t.getY()); 
			StringBuilder sb1 = new StringBuilder();  //isto kao i kod ukrstanja moramo uraditi okretanje bita
			sb1.append(konverzija);					//da bi dobili iste tezine bita kao u algoritmu
			sb1.reverse();
			String konverzija1 = sb1.toString();
			if (randVjerovatnoce.nextDouble() < Pomocna.vjerovatnocaMutacije) {
				int brojBitaUTackiGdjeSeVrsiMutacija = randTackeMutacije.nextInt((int) Pomocna.n * 2);  //generisanje slucajnog broja koji ce odrediti na kom bitu ce doci do mutacije
				char niz[] = konverzija1.toCharArray();
				if (niz[brojBitaUTackiGdjeSeVrsiMutacija] == '1')  //zamjena bita u tacki
					niz[brojBitaUTackiGdjeSeVrsiMutacija] = '0';		//ako je 0->1, a ako je 1->0
				else
					niz[brojBitaUTackiGdjeSeVrsiMutacija] = '1';
				String prva = String.copyValueOf(niz, 0, Pomocna.n); //tacka se dijeli na 2 jednaka dijela da bi se dobile kordinate
				String druga = String.copyValueOf(niz, Pomocna.n, (Pomocna.n * 2) - Pomocna.n);
				int x1 = Integer.parseInt(prva, 2);  //konverzija kordinate u bd
				int y1 = Integer.parseInt(druga, 2);
				listaNoveGeneracijeTacaka.add(new Tacka(Pomocna.konverzijaBDuX(x1), Pomocna.konverzijaBDuX(y1)));  //nova lista tacaka koja je dobijena kreiranjem novih tacaka uz pomoc funkcije koja pretvara bd u x
			} else {

				listaNoveGeneracijeTacaka.add(t);
			}
		}

	}

}
