package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Brew;

@Repository
public interface BrewRepository extends JpaRepository<Brew, Long>{

	@Query("from Brew b where b.beginning < current_date() and b.endDate is null")
	public Brew findCurrentBrew();

}
