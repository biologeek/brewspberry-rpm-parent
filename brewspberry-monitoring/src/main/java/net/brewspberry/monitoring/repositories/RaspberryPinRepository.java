package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.RaspberryPin;

@Repository
public interface RaspberryPinRepository extends JpaRepository<RaspberryPin, Long> {

}
