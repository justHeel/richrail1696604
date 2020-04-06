package com.richrail.persistency;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.richrail.domain.RichRail;
import com.richrail.domain.Train;


public class XML implements IPersist{

	@Override
	public void save(List<Train> trains) {
		try {
			// Create JAXB Context
			RichRail rRail = new RichRail(trains);
			JAXBContext jaxbContext = JAXBContext.newInstance(RichRail.class);

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Store XML to File
			File file = new File("richrail.xml");

			// Writes XML file to file-system
			jaxbMarshaller.marshal(rRail, file);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("Couldn't log to richrail.xml");
		}

	}

}
