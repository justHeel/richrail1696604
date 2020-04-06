package com.richrail.persistency;

import java.util.List;

import com.richrail.domain.Train;

public class Persist {
	private List<IPersist> persists;
	
	public Persist(List<IPersist> perists) {
		this.persists = perists;
	}
	
	public void save(List<Train> trains) {
		for(IPersist p : this.persists) {
			p.save(trains);
		}	
	}
	
}
