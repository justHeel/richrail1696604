package com.richrail.log;

import java.util.ArrayList;
import java.util.List;

public interface ILog {
	List<String> logs = new ArrayList<String>();
	void log();
	void addLog(String log);
}
