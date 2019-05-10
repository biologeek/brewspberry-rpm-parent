package net.brewspberry.brewery.mappers;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.model.Range;

@Component
public class RangeMapper {

	public Range toModel(net.brewspberry.brewery.api.Range dtoRange) {
		if (dtoRange == null)
			return null;
		Range model = new Range();
		model.setLow(dtoRange.getLow());
		model.setHigh(dtoRange.getHigh());
		model.setUnit(dtoRange.getUnit());
		return model;
	}

}
