package com.richrail.persistency;

import java.util.List;

import com.richrail.domain.Train;

public interface IPersist {

	void save(List<Train> trains);
}
