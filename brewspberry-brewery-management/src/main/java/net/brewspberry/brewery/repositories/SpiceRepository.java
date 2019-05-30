package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Spice;

@Repository
public interface SpiceRepository extends JpaRepository<Spice, Long>{

}
