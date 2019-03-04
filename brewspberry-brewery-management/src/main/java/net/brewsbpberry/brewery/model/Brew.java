package net.brewsbpberry.brewery.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Represents a brew, meaning a session of brewing operated by a brewer. <br>
 * <br>
 * It is composed of a set of ingredients referenced in configuration, a set of
 * steps (the recipe). <br>
 * <br>
 * Each brew can be named and, to locate in time each brew, beginning and end
 * dates. <br>
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
	@GeneratedValue
	private Long id;
	private String title;
	@OneToMany(mappedBy = "brew")
	private List<Step> steps;
	@OneToMany(mappedBy = "brew")
	private List<Hop> hops;
	@OneToMany(mappedBy = "brew")
	private List<Malt> malts;
	@OneToMany(mappedBy = "brew")
	private List<Yeast> yeasts;
	@OneToMany(mappedBy = "brew")
	private List<Spice> spices;
	private Date beginning, end, creationDate, updateDate;

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

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
