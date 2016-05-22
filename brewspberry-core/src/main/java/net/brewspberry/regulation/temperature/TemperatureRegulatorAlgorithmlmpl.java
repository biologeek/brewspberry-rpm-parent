package net.brewspberry.regulation.temperature;

import java.util.List;

import net.brewspberry.business.beans.TemperatureAlgorithmData;
import net.brewspberry.regulation.IRegulationAlgorithm;

public class TemperatureRegulatorAlgorithmlmpl implements IRegulationAlgorithm<TemperatureAlgorithmData> {

	@Override
	public List<TemperatureAlgorithmData> calculateVariationSpeed(
			List<TemperatureAlgorithmData> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean returnBinaryAnswerFromDataset(
			List<TemperatureAlgorithmData> dataset) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int returnIntegerAnswerFromDataset(
			List<TemperatureAlgorithmData> dataset) {
		// TODO Auto-generated method stub
		return 0;
	}

}
