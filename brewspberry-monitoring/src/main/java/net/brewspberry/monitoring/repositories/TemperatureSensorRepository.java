package net.brewspberry.monitoring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.TemperatureSensor;

@Repository
public interface TemperatureSensorRepository extends CrudRepository<TemperatureSensor, Long> {

	public TemperatureSensor findByUuid(String uuid);

}
