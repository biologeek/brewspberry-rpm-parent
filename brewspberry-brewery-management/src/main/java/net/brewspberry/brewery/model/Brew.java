package net.brewspberry.brewery.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Represents a brew, meaning a session of brewing operated by a brewer. <br>
 * <br>
 * It is composed of a set of ingredients referenced in configuration, a set of
 * steps (the recipe). <br>
 * <br>
 * Each brew can be named and, to locate in time each brew, beginning and end
 * dates. These dates are real beginning and end of brewing process.<br>
 * <br>
 * As a brew can be planned before it starts, a creation date and update date
 * are set automatically at creation or at each update. <br>
 * <br>
 * 
 * At the end of brewing, and to be able to keep trace of production, brew is
 * also composed of a production object
 * 
 * @author xavier
 *
 */
@Entity
public class Brew {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;

	@OneToMany(mappedBy = "brew")
	private List<Step> steps;
	@ManyToMany
	@JoinTable(name = "brew_ingredient", joinColumns = @JoinColumn(name = "brew_id"), inverseJoinColumns = @JoinColumn(name = "ing_id"))
	private List<Hop> hops;
	@ManyToMany
	@JoinTable(name = "brew_ingredient", joinColumns = @JoinColumn(name = "brew_id"), inverseJoinColumns = @JoinColumn(name = "ing_id"))
	private List<Malt> malts;
	@ManyToMany
	@JoinTable(name = "brew_ingredient", joinColumns = @JoinColumn(name = "brew_id"), inverseJoinColumns = @JoinColumn(name = "ing_id"))
	private List<Yeast> yeasts;
	@ManyToMany
	@JoinTable(name = "brew_ingredient", joinColumns = @JoinColumn(name = "brew_id"), inverseJoinColumns = @JoinColumn(name = "ing_id"))
	private List<Spice> spices;
	@ManyToMany
	@JoinTable(name = "brew_ingredient", joinColumns = @JoinColumn(name = "brew_id"), inverseJoinColumns = @JoinColumn(name = "ing_id"))
	private List<Additive> additives;
	private LocalDateTime beginning, creationDate, updateDate;
	private LocalDateTime endDate;

	@OneToOne
	private ProductionReport production;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<Hop> getHops() {
		return hops;
	}

	public void setHops(List<Hop> hops) {
		this.hops = hops;
	}

	public List<Malt> getMalts() {
		return malts;
	}

	public void setMalts(List<Malt> malts) {
		this.malts = malts;
	}

	public List<Yeast> getYeasts() {
		return yeasts;
	}

	public void setYeasts(List<Yeast> yeasts) {
		this.yeasts = yeasts;
	}

	public List<Spice> getSpices() {
		return spices;
	}

	public void setSpices(List<Spice> spices) {
		this.spices = spices;
	}

	public List<Additive> getAdditives() {
		return additives;
	}

	public void setAdditives(List<Additive> additives) {
		this.additives = additives;
	}

	public LocalDateTime getBeginning() {
		return beginning;
	}

	public void setBeginning(LocalDateTime beginning) {
		this.beginning = beginning;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime end) {
		this.endDate = end;
	}

	public ProductionReport getProduction() {
		return production;
	}

	public void setProduction(ProductionReport production) {
		this.production = production;
	}

}
