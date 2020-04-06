package com.richrail.service.implementations;

import java.util.List;

import com.richrail.domain.Train;
import com.richrail.repository.TrainDAO;
import com.richrail.repository.TrainDAOImpl;
import com.richrail.services.TrainService;

public class TrainServiceImpl implements TrainService{
	private TrainDAO trainDAO = new TrainDAOImpl();
	
	public Train getTrain(String name) {
		if (!nameIsEmpty(name) && nameExists(name)) {
			return trainDAO.getTrain(name);
		}
		return null;
	}

	@Override
	public boolean createTrain(String name) {
		if(!nameIsEmpty(name) && !nameExists(name)) {
			trainDAO.saveTrain(name);
			return true;
		}
		return false;
	}

	@Override
	public void deleteTrain(String name) {
		int trainId = trainDAO.getTrain(name).getId();
		trainDAO.deleteTrain(trainId);
	}

	@Override
	public List<Train> getAllTrains() {
		return trainDAO.getAll();		
	}
	
	public boolean nameExists(String name) {
		boolean exists = false;
		for(Train t : trainDAO.getAll()) {
			if (t.getLocomotive().getName().equals(name)) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	public boolean nameIsEmpty(String name) {
		return name.isEmpty();
	}


}
