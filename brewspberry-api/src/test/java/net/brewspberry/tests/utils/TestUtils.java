package net.brewspberry.tests.utils;

import java.util.Date;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.beans.builders.BrewBuilder;
import net.brewspberry.business.beans.builders.StepBuilder;

public class TestUtils {

	public static final Brassin SIMPLE_BREW = BrewBuilder.getInstance().id(1L).name("Test brew").begin(new Date())
			.end(new Date(new Date().getTime() - 3600)).quantity(50D).status(10).build();

	public static final Etape SIMPLE_STEP1 = StepBuilder.getInstance().id(1L).name("Test brew").end(new Date())
			.begin(new Date(new Date().getTime() - 3600)).remark("test remark").stageTye(PalierType.MASH_OUT).build();

}
