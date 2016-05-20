package net.brewspberry.util;

public enum Formats {

	
	STANDARD_DATE_FORMAT("yyyy-mm-dd'T'hh:mm:ss");
	
	//STANDARD_DATE_FORMAT_WITH_MS("yyyy-mm-dd hh:mm:ss.SSSSS");
	
	
	private String format;

	Formats (String format){
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
