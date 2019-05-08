package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.TemperatureStageOperation;

@Repository
public interface TemperatureStageOperationRepository extends JpaRepository<TemperatureStageOperation, Long>{

}
