package com.richrail.services;

import java.util.List;

import com.richrail.domain.Train;

public interface TrainService {
	
	boolean createTrain(String trainName);

	void deleteTrain(String trainName);

	List<Train> getAllTrains();
	
	Train getTrain(String trainName);

}