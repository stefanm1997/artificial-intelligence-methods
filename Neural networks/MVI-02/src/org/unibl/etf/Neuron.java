package org.unibl.etf;

public class Neuron {

	private int id, sloj;
	private double ulaz, izlaz;

	public Neuron() {
		super();

	}

	public Neuron(int id, int sloj, double ulaz, double izlaz) {
		super();
		this.id = id;
		this.sloj = sloj;
		this.ulaz = ulaz;
		this.izlaz = izlaz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSloj() {
		return sloj;
	}

	public void setSloj(int sloj) {
		this.sloj = sloj;
	}

	public double getUlaz() {
		return ulaz;
	}

	public void setUlaz(double ulaz) {
		this.ulaz = ulaz;
	}

	public double getIzlaz() {
		return izlaz;
	}

	public void setIzlaz(double izlaz) {
		this.izlaz = izlaz;
	}
}
