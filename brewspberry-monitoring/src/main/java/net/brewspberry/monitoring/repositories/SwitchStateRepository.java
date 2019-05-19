package net.brewspberry.monitoring.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.SwitchState;

@Repository
public interface SwitchStateRepository extends JpaRepository<SwitchState, Long> {

	@Query("select a from SwitchState a where a.device = ?1 and a.date between ?2 and ?3")
	public List<SwitchState> getSwitchStatesForDeviceAndDates(BinarySwitch device, LocalDateTime start, LocalDateTime end);
}
