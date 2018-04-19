package net.brewspberry.monitoring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.BinarySwitch;

@Repository
public interface BinarySwitchRepository extends CrudRepository<BinarySwitch, Long>{

}
