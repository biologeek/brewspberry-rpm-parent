package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.front.ws.beans.responses.MergedTemperatureMeasurementsForChart;
import net.brewspberry.front.ws.beans.responses.TemperatureChartData;

public class TemperatureMeasurementDTO {

	private Long lastID;
	
	public TemperatureMeasurementDTO() {
		lastID = 0L;
	}

	public MergedTemperatureMeasurementsForChart convertToMergedAPIObject(List<ConcreteTemperatureMeasurement> list) {

		MergedTemperatureMeasurementsForChart result = new MergedTemperatureMeasurementsForChart();

		result.setConcretes(new ArrayList<TemperatureChartData>());
		result.setTheoreticals(new ArrayList<TemperatureChartData>());

		convertToChart(list, result.getConcretes());

		result.setLastID(lastID);
		return result;
	}

	public List<TemperatureChartData> convertToChart(List<ConcreteTemperatureMeasurement> list,
			List<TemperatureChartData> result) {
		/*
		 * 1 serie per probe but keeping list of series for front compatibility
		 */
		for (ConcreteTemperatureMeasurement mes : list) {

			if (mes.getTmes_id() > lastID)
				lastID = mes.getTmes_id();
			
			
			int currentSerieIndex = 0;
			Integer i = 0;
			if (result.size() == 0) {
				i = -1;
			}
			if (i > -1) {
				for (i = 0; i < result.size(); i++) {

					System.out.println(result.get(i) + "name : " + mes.getTmes_probe_name());
					if (!result.get(i).getSeries().isEmpty()
							&& result.get(i).getSeries().contains(mes.getTmes_probe_name())) {

						currentSerieIndex = i;

						break;
					} else {
						i = -1;
					}
				}
			}
			/*
			 * Initializing chart fields as it is a new serie
			 */
			if (i == -1) {

				TemperatureChartData newData = new TemperatureChartData();

				newData.setData(new ArrayList<Float>());
				newData.setLabels(new ArrayList<String>());
				newData.setSeries(new ArrayList<String>());

				result.add(newData);

				i = result.size() - 1;
				result.get(i).getSeries().add(mes.getTmes_probe_name());

			}
			System.out.println("i " + i);

			result.get(i).getData().add(mes.getTmes_value());
			result.get(i).getLabels().add(String.valueOf(mes.getTmes_date().getTime()));
		}
		

		return result;
	}

}