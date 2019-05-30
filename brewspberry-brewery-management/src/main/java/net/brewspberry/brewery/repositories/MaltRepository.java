package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Malt;

@Repository
public interface MaltRepository extends JpaRepository<Malt, Long>{

}
