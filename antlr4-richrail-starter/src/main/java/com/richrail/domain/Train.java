package com.richrail.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "train")
@XmlAccessorType (XmlAccessType.FIELD)
public class Train {
	
	private Locomotive locomotive;
	private List<Wagon> wagons;
	private int id;

	public Train() {
	}
	
	public Train(String name) {
		this.locomotive = new Locomotive(name);
		this.wagons = new ArrayList<Wagon>();
	}
	
	public Locomotive getLocomotive() {
		return locomotive;
	}
	
	public void addWagon(Wagon wagon) {
		wagons.add(wagon);
	}
	
	public void removeWagon(Wagon wagon) {
		wagons.remove(wagon);
	}
	
	public void removeWagon(int index) {
		wagons.remove(index);
	}
	
	public List<Wagon> getWagons() {
		return wagons;
	}
	
	public void setWagons(List<Wagon> wagons) {
		this.wagons = wagons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String trainString = locomotive.getName()+": ";
		for (Wagon wagon : wagons) {
			trainString += wagon + " - "+ "\n";
		}
		trainString += "\n";
		return trainString;
	}
	
	
	
	
}
