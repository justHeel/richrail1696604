package com.richrail.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wagon")
@XmlAccessorType (XmlAccessType.FIELD)
public abstract class Wagon {
	protected int id;
	protected int seats;
	protected int train_id;
	protected String name;
	protected String wagonType;

	public Wagon(String name) {
		this.name = name;
	}
	
	public Wagon() {
	}
	
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	public int getSeats() {
		return seats;
	}

	public int getTrainId() {
		return train_id;
	}

	public void setTrainId(int id) {
		this.train_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWagonType() {
		return wagonType;
	}
	
	public void setWagonType(String type) {
		this.wagonType = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return name + ", seats " + seats + ", type " + wagonType;
	}
		
}
