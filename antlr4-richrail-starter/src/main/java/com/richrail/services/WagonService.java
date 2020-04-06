package com.richrail.services;

import java.util.List;

import com.richrail.domain.Train;
import com.richrail.domain.Wagon;

public interface WagonService {

	boolean createWagon(Train train, String name, String type);
	
	boolean createWagon(String name, String type);

	boolean deleteWagon(Wagon wagon, String name);

	void getNumSeats(Wagon wagon);
	
	List<Wagon> getAllWagons();
	
	Wagon getWagon(int id);
	
	Wagon getWagon(String name);
}
