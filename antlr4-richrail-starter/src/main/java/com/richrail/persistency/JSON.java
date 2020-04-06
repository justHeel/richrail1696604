package com.richrail.persistency;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.richrail.domain.RichRail;
import com.richrail.domain.Train;

public class JSON implements IPersist {
	private Gson gson;
	
	public JSON() {
		this.gson = new Gson();
	}
	
	@Override
	public void save(List<Train> trains) {
		RichRail rRail = new RichRail(trains);
		
		// Save java objects to richrail.json
		try (FileWriter writer = new FileWriter("richrail.json")) {
			gson.toJson(rRail, writer);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't save to richrail.json");
		}

	 }
}
