package net.brewspberry.monitoring.api;


import java.util.List;

public class ChartDto {

	
	private List<Double> data;
	private List<String> labels;
	private List<String> series;
	
	
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
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
