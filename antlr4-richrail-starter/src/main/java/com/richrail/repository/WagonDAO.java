package com.richrail.repository;

import java.util.List;

import com.richrail.domain.Wagon;

public interface WagonDAO {
	List<Wagon> getAllWagons();
	
	void saveWagon(String name, int trainId, String type);
	void deleteWagon(String name);
	void deleteWagon(int id);
	Wagon getWagon(int id);
	Wagon getWagon(String name);
}

