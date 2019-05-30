package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Additive;

@Repository
public interface AdditiveRepository extends JpaRepository<Additive, Long>{

}
