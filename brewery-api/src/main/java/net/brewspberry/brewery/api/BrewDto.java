package net.brewspberry.brewery.api;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class BrewDto {

	private Long id;
	private String title;
	private LocalDateTime beginning;
	private LocalDateTime end;
	private Quantity totalProduced;
	private Quantity totalExpected;
	private List<StepFull> steps;
	private List<Malt> malts;
	private List<Hop> hops;
	private List<Yeast> yeasts;
	private List<Spice> spices;
	private List<Additive> additives;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Additive> getAdditives() {
		return additives;
	}

	public void setAdditives(List<Additive> additives) {
		this.additives = additives;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<StepFull> getSteps() {
		return steps;
	}

	public void setSteps(List<StepFull> steps) {
		this.steps = steps;
	}

	public LocalDateTime getBeginning() {
		return beginning;
	}

	public void setBeginning(LocalDateTime beginning) {
		this.beginning = beginning;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Quantity getTotalProduced() {
		return totalProduced;
	}

	public void setTotalProduced(Quantity totalProduced) {
		this.totalProduced = totalProduced;
	}

	public Quantity getTotalExpected() {
		return totalExpected;
	}

	public void setTotalExpected(Quantity totalExpected) {
		this.totalExpected = totalExpected;
	}

	public List<Malt> getMalts() {
		return malts;
	}

	public void setMalts(List<Malt> malts) {
		this.malts = malts;
	}

	public List<Hop> getHops() {
		return hops;
	}

	public void setHops(List<Hop> hops) {
		this.hops = hops;
	}

	public List<Spice> getSpices() {
		return spices;
	}

	public void setSpices(List<Spice> spices) {
		this.spices = spices;
	}

	public List<Yeast> getYeasts() {
		return yeasts;
	}

	public void setYeasts(List<Yeast> yeasts) {
		this.yeasts = yeasts;
	}

}
