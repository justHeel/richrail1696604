package com.richrail.repository;

import java.util.List;

import com.richrail.domain.Train;

public interface TrainDAO {
	List<Train> getAll();
	void saveTrain(String name);
	void deleteTrain(String name, int id);
	void deleteTrain(int trainId);
	Train getTrain(String name);
}
	
	