package net.brewspberry.brewery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewspberry.brewery.model.Brew;
import net.brewspberry.brewery.api.BrewDto;

@Component
public class BrewMapper {

	@Autowired
	private HopMapper hopMapper;
	@Autowired
	private MaltMapper maltMapper;
	@Autowired
	private YeastMapper yeastMapper;
	@Autowired
	private AdditiveMapper additivesMapper;

	public List<BrewDto> toDto(List<Brew> brews) {
		return brews.stream().map(t -> this.toDto(t)).collect(Collectors.toList());
	}

	public BrewDto toDto(Brew model) {
		BrewDto res = new BrewDto();
		res.setId(model.getId());
		res.setBeginning(model.getBeginning());
		res.setEnd(model.getEndDate());
		res.setTitle(model.getTitle());
		res.setHops(this.hopMapper.toDto(model.getHops()));
		res.setMalts(this.maltMapper.toDto(model.getMalts()));
		res.setYeasts(yeastMapper.toDto(model.getYeasts()));
		return res;
	}

	public Brew toModel(BrewDto brew) {
		if (brew == null)
			return null;

		Brew model = new Brew();
		model.setTitle(brew.getTitle());
		model.setHops(hopMapper.toModel(brew.getHops()));
		model.setAdditives(additivesMapper.toModel(brew.getAdditives()));
		model.setBeginning(brew.getBeginning());
		model.setEndDate(brew.getEnd());
		model.setMalts(maltMapper.toModel(brew.getMalts()));
		model.setYeasts(yeastMapper.toModel(brew.getYeasts()));
		model.setId(brew.getId());
		
		return model;
	}

}
