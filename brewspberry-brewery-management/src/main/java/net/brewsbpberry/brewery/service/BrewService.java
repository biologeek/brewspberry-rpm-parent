package net.brewsbpberry.brewery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.brewsbpberry.brewery.model.Brew;

@Service
public interface BrewService {

	List<Brew> getAllBrews();

}
