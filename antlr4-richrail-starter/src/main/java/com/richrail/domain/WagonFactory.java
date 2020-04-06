package com.richrail.domain;

import java.util.Collection;
import java.util.HashMap;

public class WagonFactory {

	HashMap<String, Wagon> types;
	
	public WagonFactory() {
		this.types = registerTypes();
	}
	
	public Wagon create(String type) {
		if (type == "wagonOne") {
			return new WagonOne();
		} else if(type == "wagonTwo") {
			return new WagonTwo();
		} else {
			return new WagonThree();
		}
	}
	
	private HashMap<String, Wagon> registerTypes() {
		HashMap<String,Wagon> wagons = new HashMap<String,Wagon>();
		wagons.put("wagonOne", new WagonOne());
		wagons.put("wagonTwo", new WagonTwo());
		wagons.put("wagonThree", new WagonThree());
		
		return wagons;
	}
	
	public int seatsByType(String type) {
		int seats = 0;
		Collection<Wagon> values = types.values();
	    for (Wagon w : values) {
	       if(w.getWagonType() == type) {
	    	   seats = w.getSeats();
	    	   break;
	        }
	    }
	    return seats;
	}
}
