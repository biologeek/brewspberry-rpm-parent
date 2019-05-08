package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long>{	
	
}
