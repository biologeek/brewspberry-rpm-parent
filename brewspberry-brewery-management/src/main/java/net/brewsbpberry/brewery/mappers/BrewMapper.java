package net.brewsbpberry.brewery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewsbpberry.brewery.model.Brew;
import net.brewspberry.brewery.api.BrewDto;

@Component
public class BrewMapper {

	@Autowired
	private HopMapper hopMapper;
	@Autowired
	private MaltMapper maltMapper;
	@Autowired
	private YeastMapper yeastMapper;

	public List<BrewDto> toDto(List<Brew> brews) {
		return brews.stream().map(t -> this.toDto(t)).collect(Collectors.toList());
	}

	private BrewDto toDto(Brew model) {
		BrewDto res = new BrewDto();
		
		res.setBeginning(model.getBeginning());
		res.setEnd(model.getEnd());
		res.setTitle(model.getTitle());
		res.setHops(this.hopMapper.toDto(model.getHops()));
		return null;
	}

}
