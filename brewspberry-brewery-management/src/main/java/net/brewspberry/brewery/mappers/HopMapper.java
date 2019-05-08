package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Hop;
import net.brewspberry.brewery.api.Unit;
import net.brewspberry.brewery.model.CustomQuantity;
import net.brewspberry.brewery.model.Hop.HopType;

@Component
public class HopMapper extends AbstractIngredientMapper<net.brewspberry.brewery.model.Hop, Hop> {

	public List<Hop> toDto(List<net.brewspberry.brewery.model.Hop> ingredients) {
		return null;
	}
	
	public Hop toDto(net.brewspberry.brewery.model.Hop ingredient) {
		Hop result = toDtoAbstract(ingredient);
		return result;
	}

	@Override
	protected Hop initDto() {
		return new Hop();
	}

	public List<net.brewspberry.brewery.model.Hop> toModel(List<Hop> hops) {
		if (hops == null || hops.isEmpty())
			return new ArrayList<>();
		return null;
	}
	
	public net.brewspberry.brewery.model.Hop toModel(Hop hop){
		if (hop == null)
			return null;
		
		net.brewspberry.brewery.model.Hop res = toModelAbstract(hop);
		
		res.setAlphaAcid(new CustomQuantity(hop.getAlphaPercentage(), Unit.PERCENT));
		res.setType(HopType.valueOf(hop.getHopType()));
		return res;
	}

	@Override
	protected net.brewspberry.brewery.model.Hop initModel() {
		return new net.brewspberry.brewery.model.Hop();
	}


}
