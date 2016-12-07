package net.brewspberry.test.examples;

import net.brewspberry.main.business.beans.Biere;

public class ProductExemple {

	public static Biere aBeer() {

		return new Biere.Builder(1)//
				.alcohol(10)//
				.aroma("spicy")//
				.brew(BrewExemple.aBrew(1))//
				.bubbles(1)//
				.color(25)//
				.comment("Not that bad !")//
				.build();

	}

}
