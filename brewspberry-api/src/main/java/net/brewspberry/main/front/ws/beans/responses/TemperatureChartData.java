package net.brewspberry.main.front.ws.beans.responses;

import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * Object in charge of storing temperature measurements transmitted to front.
 * 
 * That make chart update easier in js code
 */
public class TemperatureChartData {
	
	List<Float> data;
	List<String> labels;
	List<String> series;
	
	
	public TemperatureChartData(){
		this.data = new ArrayList<>();
		this.labels = new ArrayList<>();
		this.series = new ArrayList<>();
	}
	public List<Float> getData() {
		return data;
	}
	public void setData(List<Float> data) {
		this.data = data;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
	
	
}
