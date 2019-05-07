package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.brewspberry.monitoring.model.AbstractDevice;

public interface AbstractDeviceRepository extends JpaRepository<AbstractDevice, Long> {

	AbstractDevice findByUuid(String deviceUUID);

}
