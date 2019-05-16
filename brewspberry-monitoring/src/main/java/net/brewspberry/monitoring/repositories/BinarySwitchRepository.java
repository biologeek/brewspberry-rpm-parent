package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.BinarySwitch;

@Repository
public interface BinarySwitchRepository extends JpaRepository<BinarySwitch, Long>{

	BinarySwitch findByUuid(String object);

}
