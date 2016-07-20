package net.brewspberry.test.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.TableDisplayStockCounter;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.util.StockCounterToTableStockConverter;

public class StockCounterToTableStockConverterTest {

	List<StockCounter> countersList = new ArrayList<StockCounter>();
	StockCounter stk1 = new RawMaterialCounter();

	@Before
	public void init() {

		stk1.setCpt_counter_type(new CompteurType(1, "blabla"));
		stk1.setCpt_unit(StockUnit.BOUTEILLE_33_CL);
		stk1.setCpt_value(10); // 10 33cL bottles

		AbstractIngredient ing = new SimpleHoublon();

		ing.setIng_unitary_price(2);
		ing.setIng_unitary_price_unit(StockUnit.LITRE);
		ing.setIng_unitary_weight(1);
		ing.setIng_unitary_weight_unit(StockUnit.KILO);

		((RawMaterialCounter) stk1).setCpt_product(ing);

		StockCounter stk2 = new FinishedProductCounter();

		stk2.setCpt_counter_type(new CompteurType(2, "azerty"));
		stk2.setCpt_unit(StockUnit.GRAMME);
		stk2.setCpt_value(123); // 123g
		
		AbstractFinishedProduct fp = new Biere();
		
		fp.setAfp_unitary_value(1);
		fp.setAfp_unitary_value_unit(StockUnit.BOUTEILLE_33_CL);
		fp.setStb_id(2);
		((FinishedProductCounter) stk2).setCpt_product(fp);
		
		

		countersList.add(stk1);
		countersList.add(stk2);

	}

	@Test
	public void shouldConvert() {
		Assert.assertTrue(stk1 != null);

		TableDisplayStockCounter converted = StockCounterToTableStockConverter.convert(stk1);

		Assert.assertEquals(converted.getCpt_value(), 10, 0.1); // converted to
																// litres
		Assert.assertEquals(converted.getStd_stock_value(), 6.6, 0.1); // converted
																		// to
																		// litres

	}

	@Test
	public void shouldConvertList() {

		Assert.assertTrue(countersList.size() > 0);

		List<TableDisplayStockCounter> list = StockCounterToTableStockConverter.convertList(countersList);

		Assert.assertEquals(list.size(), 2);
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void shouldSortListByType() {

		Assert.assertTrue(countersList.size() > 0);

		List<RawMaterialCounter> list = (List<RawMaterialCounter>) StockCounterToTableStockConverter
				.sortListByType(countersList, RawMaterialCounter.class);

		Assert.assertTrue(list.size() == 1);

		List<FinishedProductCounter> list1 = (List<FinishedProductCounter>) StockCounterToTableStockConverter
				.sortListByType(countersList, FinishedProductCounter.class);
		Assert.assertTrue(list1.size() == 1);
	}
}
