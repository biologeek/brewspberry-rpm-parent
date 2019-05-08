package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.AbstractIngredient;

@Repository
public interface AbstractIngredientRepository extends JpaRepository<AbstractIngredient, Long>{

}
