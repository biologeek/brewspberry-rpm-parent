package net.brewspberry.monitoring.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.TemperatureMeasurement;

@Repository
public interface TemperatureMeasurementRepository extends JpaRepository<TemperatureMeasurement, Long> {

	@Query("select a from TemperatureMeasurement a where a.sensor.uuid = ?1 and a.date between ?2 and ?3")
	List<TemperatureMeasurement> findAllByUuidBetween(String uuid, LocalDateTime begin, LocalDateTime end);

	@Query("select a from TemperatureMeasurement a where a.sensor.uuid = ?1 and a.date >= ?2")
	List<TemperatureMeasurement> findAllByUuidAfter(String uuid, LocalDateTime begin);

	@Query("select a from TemperatureMeasurement a where a.sensor.uuid = ?1 and a.date <= ?2")
	List<TemperatureMeasurement> findAllByUuidBefore(String uuid, LocalDateTime end);

}
