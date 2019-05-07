package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Malt;

@Component
public class MaltMapper extends AbstractIngredientMapper<net.brewspberry.brewery.model.Malt, Malt> {

	public List<Malt> toDto(List<net.brewspberry.brewery.model.Malt> malts) {
		if (malts == null || malts.isEmpty())
			return new ArrayList<>();
		return malts.stream().map(t -> toDto(t)).collect(Collectors.toList());
	}

	public Malt toDto(net.brewspberry.brewery.model.Malt t) {
		if (t == null)
			return null;
		Malt res = toDtoAbstract(t);
		res.setEbc(t.getEbcColor());
		res.setForm(t.getForm());
		return res;
	}

	@Override
	protected Malt initDto() {
		return new Malt();
	}

	public List<net.brewspberry.brewery.model.Malt> toModel(List<Malt> malts) {
		if (malts == null || malts.isEmpty())
			return new ArrayList<>();
		return malts.stream().map(t -> toModel(t)).collect(Collectors.toList());
	}

	public net.brewspberry.brewery.model.Malt toModel(Malt dto) {
		if (dto == null)
			return null;
		net.brewspberry.brewery.model.Malt res = toModelAbstract(dto);

		res.setEbcColor(dto.getEbc());
		res.setForm(dto.getForm());
		return res;
	}

	@Override
	protected net.brewspberry.brewery.model.Malt initModel() {
		return new net.brewspberry.brewery.model.Malt();
	}

}
