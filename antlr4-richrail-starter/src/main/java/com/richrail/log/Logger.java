package com.richrail.log;

public class Logger {
	
	private ILog log;
	
	public Logger(ILog log) {
		this.log = log;
	}
	
	public void addlog(String log) {
		this.log.addLog(log);
	}
	
	public void log() {
		this.log.log();
	}
	
	public ILog getLog() {
		return this.log;
	}

}
