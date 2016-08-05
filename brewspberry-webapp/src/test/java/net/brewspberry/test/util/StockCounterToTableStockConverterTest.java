package net.brewspberry.test.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.TableDisplayRawMaterialStockCounter;
import net.brewspberry.business.beans.TableDisplayStockCounter;
import net.brewspberry.business.beans.TableToDisplayFinishedProductCounter;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.CounterTypeConstants;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.test.util.config.WebappSpringTestConfiguration;
import net.brewspberry.test.util.config.WebappTestConfiguration;
import net.brewspberry.util.StockCounterToTableStockConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebappTestConfiguration.class)
@WebAppConfiguration
public class StockCounterToTableStockConverterTest {

	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CounterType> counterTypeService;
	List<StockCounter> countersList = new ArrayList<StockCounter>();
	
	List<TableDisplayStockCounter> tableDisplayList = new ArrayList<TableDisplayStockCounter>();
	StockCounter stk1 = new RawMaterialCounter();

	@Before
	public void init() {

		List<CounterType> list = counterTypeService.getAllElements();
		Assert.assertNotNull(list);
		stk1.setCpt_counter_type(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list));
		stk1.setCpt_unit(StockUnit.BOUTEILLE_33_CL);
		stk1.setCpt_value(10); // 10 33cL bottles


		AbstractIngredient ing = new SimpleHoublon();

		ing.setStb_id(1);
		ing.setIng_unitary_price(2);
		ing.setIng_unitary_price_unit(StockUnit.LITRE);
		ing.setIng_unitary_weight(1);
		ing.setIng_unitary_weight_unit(StockUnit.KILO);

		((RawMaterialCounter) stk1).setCpt_product(ing);

		StockCounter stk2 = new FinishedProductCounter();

		stk2.setCpt_counter_type(CounterTypeConstants.STOCK_DLC_DEPASSEE.toDBCouter(list));
		stk2.setCpt_unit(StockUnit.GRAMME);
		stk2.setCpt_value(123); // 123g
		
		AbstractFinishedProduct fp = new Biere();
		
		fp.setAfp_unitary_value(1);
		fp.setAfp_unitary_value_unit(StockUnit.BOUTEILLE_33_CL);
		fp.setStb_id(2);
		((FinishedProductCounter) stk2).setCpt_product(fp);
		
		TableDisplayRawMaterialStockCounter tbl1 = new TableDisplayRawMaterialStockCounter((RawMaterialCounter) stk1);
		TableToDisplayFinishedProductCounter tbl2 = new TableToDisplayFinishedProductCounter((FinishedProductCounter) stk2);
		tbl2.setStf_product(fp);
		
		countersList.add(stk1);
		countersList.add(stk2);

		tableDisplayList.add(tbl1);
		//tableDisplayList.add(tbl2);

	}
	
	@Test
	public void test(){
		Assert.assertTrue(true);
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

		List<TableDisplayRawMaterialStockCounter> list = (List<TableDisplayRawMaterialStockCounter>) StockCounterToTableStockConverter
				.sortListByType(tableDisplayList, RawMaterialCounter.class);

		Assert.assertTrue(list.size() == 1);

		List<FinishedProductCounter> list1 = (List<FinishedProductCounter>) StockCounterToTableStockConverter
				.sortListByType(tableDisplayList, FinishedProductCounter.class);
		Assert.assertTrue(list1.size() == 1);
	}
}
