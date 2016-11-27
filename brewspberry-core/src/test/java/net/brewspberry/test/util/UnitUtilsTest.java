package net.brewspberry.test.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.JUnit4;
import org.mockito.internal.runners.JUnit44RunnerImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.exceptions.UnitConversionFailedException;
import net.brewspberry.main.util.StockUnitUtils;
import net.brewspberry.test.AbstractTest;

@RunWith(JUnit4.class)
public class UnitUtilsTest {

	@Test
	public void shouldConvertToOtherUnit() {

		double value = 10;
		StockUnit unitFrom = StockUnit.BOUTEILLE_33_CL;
		StockUnit unitTo = StockUnit.FUT_30_L;
		double result = 0;
		try {
			result = StockUnitUtils.convertToOtherUnit(value, unitFrom, unitTo);
		} catch (UnitConversionFailedException e) {
			
			e.printStackTrace();
		}

		Assert.assertEquals(0.11, result, 0.01);

		value = 10;
		unitFrom = StockUnit.SAC_5_KG;
		unitTo = StockUnit.SAC_25_KG;
		result = 0;
		try {
			result = StockUnitUtils.convertToOtherUnit(value, unitFrom, unitTo);
		} catch (UnitConversionFailedException e) {
			
			e.printStackTrace();
		}

		Assert.assertEquals(2, result, 0.01);

	}

	@Test
	public void shouldConvertToParentUnit() {

		double value = 10;
		StockUnit unitFrom = StockUnit.BOUTEILLE_75_CL;

		double result = StockUnitUtils.convertToStandardUnit(value, unitFrom);

		StockUnit unitTo = StockUnitUtils
				.getStandardUnitFromNonStandardUnit(unitFrom);

		Assert.assertEquals(7.5, result, 0.1);

		Assert.assertEquals(StockUnit.LITRE, unitTo);

		value = 5.2;
		unitFrom = StockUnit.METRE;

		result = StockUnitUtils.convertToStandardUnit(value, unitFrom);
		unitTo = StockUnitUtils.getStandardUnitFromNonStandardUnit(unitFrom);

		Assert.assertEquals(5.2, result, 0.1);

		Assert.assertEquals(StockUnit.METRE, unitTo);

	}
}
