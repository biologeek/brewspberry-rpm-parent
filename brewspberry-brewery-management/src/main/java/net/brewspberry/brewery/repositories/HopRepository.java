package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Hop;

@Repository
public interface HopRepository extends JpaRepository<Hop, Long>{

}
