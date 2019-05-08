package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.QuantifiedIngredient;

@Repository
public interface QuantifiedIngredientRepository extends JpaRepository<QuantifiedIngredient, Long>{

}
