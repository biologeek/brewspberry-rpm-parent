package net.brewspberry.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.brewery.model.Yeast;

@Repository
public interface YeastRepository extends JpaRepository<Yeast, Long>{

}
