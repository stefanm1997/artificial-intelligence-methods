package org.unibl.etf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Pomocna {

	public static Double gg = citajIzPropertijaDouble("Gg");
	public static Double gd = citajIzPropertijaDouble("Gd");
	public static Double preciznost = citajIzPropertijaDouble("Preciznost");
	public static Integer n = brojBitaN();
	public static double velicinaPopulacije = citajIzPropertijaDouble("Velicina_populacije");
	public static double vjerovatnocaUkrstanja = citajIzPropertijaDouble("Vjerovatnoca_ukrstanja");
	public static double vjerovatnocaMutacije = citajIzPropertijaDouble("Vjerovatnoca_mutacije");
	public static String tipFunkcije=citajIzPropertijaString("Tip_funkcije");
	public static double brojIteracija = citajIzPropertijaDouble("Broj_iteracija");
	public static double ocjenaPopulacije = 0;
	public static Tacka[] nizTacaka = new Tacka[(int) Pomocna.velicinaPopulacije];
	public static ArrayList<Double> rezultatFitnesa = new ArrayList<Double>();
	public static ArrayList<Double> vjerovatnocaIzbora = new ArrayList<Double>();
	public static ArrayList<Integer> listaZaSledecuGeneraciju = new ArrayList<Integer>();
	public static ArrayList<Double> listaKumulativnihVjerovatnoca = new ArrayList<Double>();
	public static ArrayList<Tacka> listaSledeceGeneracije = new ArrayList<Tacka>();

	public static Double citajIzPropertijaDouble(String nazivPromjenjljive) { //f-ja koja cita double iz konfiguracionog fajla
		Double rezultat = null;

		try (InputStream is = new FileInputStream("src" + File.separator + "config.properties")) {
			Properties prop = new Properties();
			prop.load(is);
			rezultat = Double.valueOf(prop.getProperty(nazivPromjenjljive));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rezultat;
	}
	
	public static String citajIzPropertijaString(String nazivPromjenjljive) { //f-ja koja cita string iz konfiguracionog fajla
		String rezultat = null;

		try (InputStream is = new FileInputStream("src" + File.separator + "config.properties")) {
			Properties prop = new Properties();
			prop.load(is);
			rezultat = prop.getProperty(nazivPromjenjljive);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rezultat;
	}

	public static int log2(double x) { //f-je od 60. do 88. linije su pomocne f-je koje sluze za izracunavanje odredjenih formula koje su potreben za izvrsavanje algoritma
		return (int) (Math.log(x) / Math.log(2)); //i njihova imena govore tacno sta koja radi
	}

	public static Integer brojBitaN() {
		Integer rezultat = null;
		rezultat = log2((gg - gd) * Math.pow(10, preciznost) + 1);
		return rezultat + 1;
	}

	public static int racunajBD(double x) {
		double rezultat = 0;
		rezultat = ((x - gd) / (gg - gd) * (Math.pow(2, (double) n) - 1));
		return (int) rezultat;
	}

	public static String kodovanjeBDa(int BD) {
		return String.format("%" + n + "s", Integer.toBinaryString(BD)).replaceAll(" ", "0");
	}

	public static double konverzijaBDuX(int bd) {
		return gd + ((gg - gd) / (Math.pow(2, n) - 1) * bd);
	}

	public static double funkcija(double x) {
		return 6 * x - 3;
	}

	public static void izborPocetnePopulacije() { //f-ja koja pravi pocetnu populaciju tacaka birajuci kordinate tacaka na slucajan nacin
		Random rand = new Random();

		for (int i = 0; i < (int)velicinaPopulacije; i++) {
			double randX = rand.nextDouble();
			double randY = rand.nextDouble();
			nizTacaka[i] = new Tacka(funkcija(randX), funkcija(randY)); //tacke se dodaju u niz tacaka
		}
	}

	public static ArrayList<Double> fitnesFunkcijaMin(ArrayList<Double> al) { //f-ja koja racuna fitnes funkciju za trazenje minimuma f-je
		
		Double maks = al.get(0); //uzima se prvi element iz liste 

		for (Double list : al) { //prolazi se kroz listu koja je dobijena racunanjem odredjene f-je
			ocjenaPopulacije += maks - list; //racuna ukupnu ocjenu populacije
			rezultatFitnesa.add(maks - list); //dodaje rezultate fitnesa u listu koju vraca kao rezultat
		}									//lista vrijednosti funkcija se sortira opadajuce od najveceg do najmanje i onda se taj prvi element koji je najveci oduzima od svih ostalih vrijednosti f-je
		System.out.println("Populacija ove generacije: "+ocjenaPopulacije);
		return rezultatFitnesa; //vraca listu koja je double
	}
	
public static ArrayList<Double> fitnesFunkcijaMaks(ArrayList<Double> al) { //f-ja koja racuna fitnes funkciju za trazenje maksimuma f-je
		//moram i kontra sortirati
		Double min = al.get(0); //uzima se prvi element iz liste 

		for (Double list : al) { //prolazi se kroz listu koja je dobijena racunanjem odredjene f-je
			ocjenaPopulacije += list-min; //racuna ukupnu ocjenu populacije
			rezultatFitnesa.add(list-min); //dodaje rezultate fitnesa u listu koju vraca kao rezultat
		}							//lista vrijednosti funkcija se sortira rastuce od najmanjeg do najveceg i onda se  od svih ostalih vrijednosti f-je iz liste taj prvi element koji je najmanji oduzima
		System.out.println("Populacija ove generacije: "+ocjenaPopulacije);
		return rezultatFitnesa;  //vraca listu koja je double
	}

	public static ArrayList<Double> kumulativnaVjerovatnocaMin(ArrayList<Double> al) { //f-ja koja racuna kumulativnu vjerovatnocu za trazenje minimuma f-je
		
		Double maks = al.get(0); //uzima se prvi element iz liste 
		double suma = 0;
		for (Double list : al) { //prolazi se kroz listu koja je dobijena racunanjem odredjene f-je
			vjerovatnocaIzbora.add(vjerovatnocaIzboraJedinke(maks - list)); //u novu listu double-ova se dodaje vjervatnoca izbora jedine koja se racuna kao razlika najveceg elementa u listi i vrijednosti svih ostalih elemenata liste
			suma += vjerovatnocaIzboraJedinke(maks - list); //racuna se suma prethodno dobijenih vjerovatnoca izbora jedinke 
			listaKumulativnihVjerovatnoca.add(suma); //suma se dodaje u listu koja je tip double

		}

		return vjerovatnocaIzbora; //vraca listu koja je tipa double
	}
	
	public static ArrayList<Double> kumulativnaVjerovatnocaMaks(ArrayList<Double> al) { //f-ja koja racuna kumulativnu vjerovatnocu za trazenje maksimuma f-je
		
		Double min = al.get(0); //uzima se prvi element iz liste 
		double suma = 0;
		for (Double list : al) { //prolazi se kroz listu koja je dobijena racunanjem odredjene f-je
			vjerovatnocaIzbora.add(vjerovatnocaIzboraJedinke(list-min)); //u novu listu double-ova se dodaje vjervatnoca izbora jedine koja se racuna kao razlika vrijednosti svih ostalih elemenata liste i najmanjeg elementa u listi
			suma += vjerovatnocaIzboraJedinke(list-min); //racuna se suma prethodno dobijenih vjerovatnoca izbora jedinke 
			listaKumulativnihVjerovatnoca.add(suma); //racuna se suma prethodno dobijenih vjerovatnoca izbora jedinke 

		}

		return vjerovatnocaIzbora; //vraca listu koja je tipa double
	}

	public static double vjerovatnocaIzboraJedinke(double ff) { //f-ja koja racuna vjerovatnocu izbora odredjene jedinke
		return ff / ocjenaPopulacije; //racuna se kao kolicnik vrijednosti fitnes funkcije i ukupne ocjene populacije
	}

	public static ArrayList<Integer> izborSelekcije(ArrayList<Double> al) { //f-ja koja bira koja ce tacka ici u sledecu generaciju
		
		Random rand = new Random();

		for (int i = 0; i < (int)velicinaPopulacije; i++) { //for petlja se izvrsava onoliko puta kolika je velicina populacije
			double broj = rand.nextDouble(); //generise se slucajan broj izmedju 0 i 1
			for (int j = 0; j < (int)velicinaPopulacije; j++) { //prolazi se kroz listu kumulativnih vjerovatnoca
				if (broj < al.get(j)) { //ako je slucajno generisani broj manji od kumultaivne vjerovatnoce onda se indeks te tacke dodaje u listu, vrti se for petlja dok ne nadje odredjeni indeks 
					listaZaSledecuGeneraciju.add(j); //dodaju se indeksi u listu koja je tipa integer
					break;
				}
			}
		}
		return listaZaSledecuGeneraciju; //vraca se lista koja je tipa integer i koja sadrzi sve indekse tacaka koji prolaze u sledecu generaciju

	}

	public static void praviSledecuGeneraciju(ArrayList<Integer> listaGeneracije) { //f-ja koja pravi novu listu tacaka koja predstavlja tacke sledece generacije

		for (int i = 0; i < (int)velicinaPopulacije; i++) { //prolazi se for petljom kroz sve tacke
			int indeks = listaGeneracije.get(i); //uzimaju  se prethodno izracunati indeksi tacaka koji su prosli u sledecu generaciju iz liste tacaka
			listaSledeceGeneracije.add(nizTacaka[indeks]); //tacke koje su prosle u sledecu generaciju se dodaju u listu koja predstavlja krajnju listu sledece generacije

		}

		/* 2. nacin za ovo,preko strima
		 * listaGeneracije.forEach(e -> { listaSledeceGeneracije.add(nizTacaka[e]);
		 * System.out.println(e); System.out.println(nizTacaka[e]); });
		 */

	}
	
	public static void mijesanjeTacaka(ArrayList<Tacka> lista) { //f-ja koja na slucajan nacin mijesa pozicije tacaka
		Random rand=new Random();
		int indeks1=0,indeks2=0,br=0;
		
		while(br<(int)velicinaPopulacije/2) { //broj promjena koje ce se izvrsiti je polovina od ukupne populacije(moze se na vise nacina odrediti,ja sam ovako radio)
			indeks1=rand.nextInt((int)velicinaPopulacije); //generisu se slucajno 2 broja koji predstavljaju indekse tacaka koje ce se zamijeniti
			indeks2=rand.nextInt((int)velicinaPopulacije);
			Tacka t=lista.get(indeks1);
			lista.set(indeks1,lista.get(indeks2)); //na indeks prve tacke dolazi indeks druge tacke i obrnuto
			lista.set(indeks2, t);
			br++;
		}
		
		
	}
}
