package com.richrail.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLog implements ILog {

	
	@Override
	public void log() {
		File file = new File("richrail.txt");
		try(FileWriter fw = new FileWriter(file, true)) {
			for (String log : logs) {
				fw.write(log + "\n");
			}
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addLog(String log) {
		logs.add(log);
	}

}
