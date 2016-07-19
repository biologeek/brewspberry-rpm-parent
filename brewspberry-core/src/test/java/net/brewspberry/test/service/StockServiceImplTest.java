package net.brewspberry.test.service;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.runners.JUnit44RunnerImpl;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.dao.StockDAOImpl;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class StockServiceImplTest {

	@Autowired
	@Qualifier("stockServiceImpl")
	IGenericService<StockCounter> genStockService;
	@Autowired
	ISpecificStockService specStockService;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void shouldToogleStockCounterForProduct() {
		StockCounter res = null;

		IGenericDao<StockCounter> daoMock = Mockito.mock(IGenericDao.class);

		SimpleMalt malt = null;
		CompteurType type = null;
		buildDataset(malt, type);
		Mockito.when(daoMock.getElementById(1))
				.thenReturn((StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
						.type(type).unit(StockUnit.KILO).value(3)).ingredient(malt).build());

		try {
			res = specStockService.toogleStockCounterForProduct(-3, malt, type);
		} catch (StockException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(0, res.getCpt_value(), 0.1);

		try {
			res = specStockService.toogleStockCounterForProduct(-2, malt, type);
		} catch (StockException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test(expected=StockException.class)
	public void shouldToogleStockCounterForProductThrowException() {
		StockCounter res = null;

		IGenericDao<StockCounter> daoMock = Mockito.mock(StockDAOImpl.class);

		SimpleMalt malt = null;
		CompteurType type = null;
		buildDataset(malt, type);
		Mockito.when(daoMock.getElementById(1))
				.thenReturn((StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
						.type(type).unit(StockUnit.KILO).value(1)).ingredient(malt).build());

			try {
				res = specStockService.toogleStockCounterForProduct(-2, malt, type);
			} catch (StockException | ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void buildDataset(SimpleMalt malt, CompteurType type) {

		// 3 qty for counter 3 and 20 qty for counter 1
		malt = new SimpleMalt();
		malt.setStb_id(1);
		malt.setIng_fournisseur("Weyermann");
		malt.setIng_desc("test");
		malt.setIng_unitary_price(5);
		malt.setIng_unitary_price_unit(StockUnit.KILO);
		malt.setIng_unitary_weight(1);
		malt.setIng_unitary_weight_unit(StockUnit.KILO);
		malt.setSmal_cereale("Orge");
		malt.setSmal_couleur(3);
		malt.setSmal_type("Blond");

		type = new CompteurType();
		type.setCty_id(3);
		type.setCty_date_cre(Calendar.getInstance().getTime());
		type.setCty_libelle("Stock reserve fabrication");

	}
}
