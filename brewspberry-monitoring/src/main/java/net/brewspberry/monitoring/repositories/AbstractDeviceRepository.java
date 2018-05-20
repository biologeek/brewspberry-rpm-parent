package net.brewspberry.monitoring.repositories;

import org.springframework.data.repository.CrudRepository;

import net.brewspberry.monitoring.model.AbstractDevice;

public interface AbstractDeviceRepository extends CrudRepository<AbstractDevice, Long> {

}
