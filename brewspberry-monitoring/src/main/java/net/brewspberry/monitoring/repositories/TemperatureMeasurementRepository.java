package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.brewspberry.monitoring.model.TemperatureMeasurement;

public interface TemperatureMeasurementRepository extends JpaRepository<TemperatureMeasurement, Long> {

}
