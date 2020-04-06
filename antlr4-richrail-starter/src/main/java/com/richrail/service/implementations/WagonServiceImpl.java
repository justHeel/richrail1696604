package com.richrail.service.implementations;

import java.util.List;

import com.richrail.domain.Train;
import com.richrail.domain.Wagon;
import com.richrail.repository.WagonDAO;
import com.richrail.repository.WagonDAOImpl;
import com.richrail.services.WagonService;

public class WagonServiceImpl implements WagonService {

	private WagonDAO wagonDAO = new WagonDAOImpl();
	
	@Override
	public boolean createWagon(Train train, String name, String type) {
		if(train != null && !nameIsEmpty(name) && !nameExists(name) && type != null && !type.isEmpty()) {
			wagonDAO.saveWagon(name, train.getId(), type);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean createWagon(String name, String type) {
		if(!nameIsEmpty(name) && !nameExists(name) &&  type != null && !type.isEmpty()) {
			wagonDAO.saveWagon(name, 0, type);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteWagon(Wagon wagon, String wagonName) {
		if (wagon != null) {
			System.out.print("Wagon id = " +  wagon.getId());
			wagonDAO.deleteWagon(wagon.getId());
			return true;
		}
		return false;
	}
	
	@Override
	public void getNumSeats(Wagon wagon) {
		if (wagon != null) {
			System.out.print("Wagon seats = " +  wagon.getSeats());
		} else {
			System.out.print("Wagon not found");
		}
		
	}
	
	public boolean nameExists(String name) {
		boolean exists = false;
		for(Wagon w : wagonDAO.getAllWagons()) {
			if (w.getName().equals(name)) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	public boolean nameIsEmpty(String name) {
		return name.isEmpty();
	}

	@Override
	public List<Wagon> getAllWagons() {	
		return wagonDAO.getAllWagons();
	}

	@Override
	public Wagon getWagon(int id) {
		return wagonDAO.getWagon(id);
	}

	@Override
	public Wagon getWagon(String name) {
		return wagonDAO.getWagon(name);
	}

}
