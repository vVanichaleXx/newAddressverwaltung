package de.hup.newadressverwaltung;

import lombok.Setter;

@Setter
public class Address {

	private int id;
	private String street;
	private int postfach;
	private String plz;
	private String ort;

	public Address(int id, String street, int postfach, String plz, String ort) {
		this.id = id; // Use the ID provided by IdGenerator
		this.street = street;
		this.postfach = postfach;
		this.plz = plz;
		this.ort = ort;
	}

	public int getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public int getPostfach() {
		return postfach;
	}

	public String getPlz() {
		return plz;
	}

	public String getOrt() {
		return ort;
	}

	@Override
	public String toString() {
		return String.format("%d, %s, %d, %s, %s", id, street, postfach, plz, ort);
	}
}
