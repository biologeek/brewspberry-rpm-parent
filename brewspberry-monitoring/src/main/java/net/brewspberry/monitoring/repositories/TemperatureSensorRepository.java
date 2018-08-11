package net.brewspberry.monitoring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.TemperatureSensor;

@Repository
public interface TemperatureSensorRepository extends CrudRepository<TemperatureSensor, Long> {

	public TemperatureSensor findByUuid(String uuid);

	public List<TemperatureSensor> findAllByUuids(List<String> deviceUuids);

}
