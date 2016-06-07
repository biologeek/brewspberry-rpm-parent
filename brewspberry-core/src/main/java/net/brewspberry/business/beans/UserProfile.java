package net.brewspberry.business.beans;

import java.util.Map;

public enum UserProfile {
	
	
	boolean read, write, execute;
	Map<String, Boolean> specialAuths;
	
	
	READER(true, false, false, null);


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
