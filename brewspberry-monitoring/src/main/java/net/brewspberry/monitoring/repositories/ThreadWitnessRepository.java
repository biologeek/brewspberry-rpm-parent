package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.ThreadWitness;

@Repository
public interface ThreadWitnessRepository extends JpaRepository<ThreadWitness, Long>{

}
