package net.brewspberry.main.front.ws.beans.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.mockito.internal.listeners.CollectCreatedMocks;

import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.monitoring.MultiActionnerTemperatures;
import net.brewspberry.main.front.ws.beans.responses.MergedTemperatureMeasurementsForChart;
import net.brewspberry.main.front.ws.beans.responses.TemperatureChartData;

public class TemperatureMeasurementDTO {

	private Long lastID;

	public TemperatureMeasurementDTO() {
		lastID = 0L;
	}

	public MergedTemperatureMeasurementsForChart convertToMergedAPIObject(List<ConcreteTemperatureMeasurement> list) {

		MergedTemperatureMeasurementsForChart result = new MergedTemperatureMeasurementsForChart();

		result.setConcretes(new ArrayList<TemperatureChartData>());
		result.setTheoreticals(new ArrayList<TemperatureChartData>());

		convertToChart(list);

		result.setLastID(lastID);
		return result;
	}

	/**
	 * Transforms a unordered list of temperatureMeasurements to an ordered and
	 * sorted API object
	 * 
	 * @param list
	 * @return
	 */
	public List<TemperatureChartData> convertToChart(List<ConcreteTemperatureMeasurement> list) {

		Collections.sort(list, new Comparator<ConcreteTemperatureMeasurement>() {

			@Override
			public int compare(ConcreteTemperatureMeasurement o1, ConcreteTemperatureMeasurement o2) {
				if (o1.getTmes_date().before(o2.getTmes_date())) {
					return -1;
				} else if (o1.getTmes_date().equals(o2.getTmes_date()))
					return 0;
				else
					return 1;
			}
		});

		List<TemperatureChartData> result = new ArrayList<>();

		for (ConcreteTemperatureMeasurement temp : list) {

			TemperatureChartData currentChart = null;
			currentChart = this.findByUUID(temp.getTmes_probeUI(), result);

			if (currentChart == null) {
				currentChart = new TemperatureChartData();

				currentChart.getSeries().add(temp.getTmes_probeUI());
				result.add(currentChart);
			} else {
				currentChart.getData().add(temp.getTmes_value());
				currentChart.getLabels().add(String.valueOf(temp.getTmes_date().getTime()));
			}

		}
		return result;
	}

	/**
	 * Searches in result if a TemperatureChartData matches uuid passed in first
	 * parameter.
	 * 
	 * If so, returns a reference to the object in result. Else null
	 * 
	 * @param uuid
	 * @param result
	 * @return
	 */
	private TemperatureChartData findByUUID(String uuid, List<TemperatureChartData> result) {
		if (uuid != null && !uuid.equals("")) {
			if (result != null && !result.isEmpty()) {
				for (TemperatureChartData data : result) {
					if (data.getSeries().get(0).equals(uuid)) {
						return data;
					}
				}
			}
		}
		return null;
	}

	public List<MergedTemperatureMeasurementsForChart> convertToMergedAPIObject(
			MultiActionnerTemperatures temperaturesForActionners) {

		List<MergedTemperatureMeasurementsForChart> result = new ArrayList<>();
		Set<String> keySet = temperaturesForActionners.getTemperatures().keySet();

		for (String key : keySet) {

			List<ConcreteTemperatureMeasurement> obj = temperaturesForActionners.getTemperatures().get(key);

			List<TemperatureChartData> data = this.convertToChart(obj);

			for (TemperatureChartData unit : data) {
				MergedTemperatureMeasurementsForChart chart = new MergedTemperatureMeasurementsForChart();
				chart.setConcretes(new ArrayList<>());
				chart.getConcretes().add(unit);
				
				chart.setTheoreticals(new ArrayList<>());

				chart.setLastID(0L);
				result.add(chart);
			}
		}

		return result;
	}

}
