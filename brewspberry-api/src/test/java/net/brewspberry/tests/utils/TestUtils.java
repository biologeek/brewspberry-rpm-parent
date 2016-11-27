package net.brewspberry.tests.utils;

import java.util.Date;

import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.BrewStatus;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.PalierType;
import net.brewspberry.main.business.beans.builders.BrewBuilder;
import net.brewspberry.main.business.beans.builders.StepBuilder;

public class TestUtils {

	public static final Brassin SIMPLE_BREW = new BrewBuilder().id(1L).name("Test brew").begin(new Date())
			.end(new Date(new Date().getTime() - 3600)).quantity(50D).status(BrewStatus.NOT_STARTED).build();

	public static final Etape SIMPLE_STEP1 = new StepBuilder().id(1L).name("Test brew").end(new Date())
			.begin(new Date(new Date().getTime() - 3600)).remark("test remark").stageType(PalierType.MASH_OUT).build();

}
