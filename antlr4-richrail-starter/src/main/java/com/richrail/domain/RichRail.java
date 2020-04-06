package com.richrail.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "richrail")
@XmlAccessorType (XmlAccessType.FIELD)
public class RichRail {
	
	List<Train> trains;
	
	public RichRail() {
	}
	
	public RichRail(List<Train> trains) {
		this.trains = trains;
	}
		
	public List<Train> getTrains() {
		return this.trains;
	}

}
