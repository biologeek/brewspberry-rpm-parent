package net.brewspberry.regulation.temperature;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.brewspberry.business.beans.TemperatureAlgorithmData;
import net.brewspberry.regulation.IRegulationAlgorithm;

public class TemperatureRegulatorAlgorithmlmpl implements IRegulationAlgorithm<TemperatureAlgorithmData> {

	@Override
	public List<TemperatureAlgorithmData> calculateVariationSpeed(
			List<TemperatureAlgorithmData> data) {
		
		if (data != null && data.size() > 0){
			
			Collections.sort(data, new Comparator<TemperatureAlgorithmData>(){

				@Override
				public int compare(TemperatureAlgorithmData o1,
						TemperatureAlgorithmData o2) {
					
					if (o1.getDate().after(o2.getDate())){
						return 1;
					}
					else if (o1.getDate().before(o2.getDate())){
							return 0;
					}
					else{
						return -1;
					}
				}
			});
			
			
			
		}
		
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
