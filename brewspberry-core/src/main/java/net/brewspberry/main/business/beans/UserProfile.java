package net.brewspberry.main.business.beans;

import java.util.Map;

import org.springframework.stereotype.Component;

public enum UserProfile {
	
	
	READER(true, false, false, null),
	WRITER(true, true, false, null),
	BREWER(true, true, true, null),
	MASTER(true, true, true, null);
	
	boolean read, write, execute;
	Map<String, Boolean> specialAuths;
	
	


	private UserProfile(boolean read, boolean write, boolean execute, Map<String, Boolean> specialAuths) {
		this.read = read;
		this.write = write;
		this.execute = execute;
		this.specialAuths = specialAuths;
	}
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isWrite() {
		return write;
	}
	public void setWrite(boolean write) {
		this.write = write;
	}
	public boolean isExecute() {
		return execute;
	}
	public void setExecute(boolean execute) {
		this.execute = execute;
	}
	public Map<String, Boolean> getSpecialAuths() {
		return specialAuths;
	}
	public void setSpecialAuths(Map<String, Boolean> specialAuths) {
		this.specialAuths = specialAuths;
	}
	
}
