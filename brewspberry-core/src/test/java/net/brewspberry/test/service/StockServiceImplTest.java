package net.brewspberry.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javassist.compiler.ast.Stmnt;

import org.junit.Assert;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.business.beans.stock.AbstractStockMotion;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.dao.StockDAOImpl;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.test.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class StockServiceImplTest extends AbstractTest {

	@Autowired
	@Qualifier("stockServiceImpl")
	IGenericService<StockCounter> genStockService;

	@Mock
	ISpecificStockDao specDaoMock;

	@Mock
	private IGenericDao<StockCounter> genericDAO;

	@Mock
	private StockCounter cptToDecrease;

	@Autowired
	@InjectMocks
	ISpecificStockService specStockService;

	StockCounter res = null;

	SimpleMalt malt;
	SimpleHoublon houblon;
	CounterType type;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);

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

		houblon = new SimpleHoublon();
		houblon.setIng_desc("test hop");
		houblon.setIng_fournisseur("Weyermann");
		houblon.setIng_unitary_price(90);
		houblon.setIng_unitary_price_unit(StockUnit.KILO);
		houblon.setIng_unitary_weight(100);
		houblon.setIng_unitary_weight_unit(StockUnit.GRAMME);

		type = CounterType.STOCK_RESERVE_FAB;

		StockCounter stl = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(type).unit(StockUnit.KILO).value(3)).ingredient(malt).build();

		StockCounter st2 = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(CounterType.STOCK_DISPO_FAB).unit(StockUnit.SAC_25_KG).value(10)).ingredient(malt).build();


		StockCounter st3 = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(CounterType.STOCK_DISPO_FAB).unit(StockUnit.GRAMME).value(200)).ingredient(houblon).build();
		
		
		Mockito.when(specDaoMock.getStockCounterByProductAndType(malt, type)).thenReturn(stl);

		Mockito.when(genericDAO.update(cptToDecrease)).thenReturn(stl);

	}

	@Test
	public void shouldToogleStockCounterForProduct() {

		Assert.assertNotNull(specStockService);
		try {
			res = specStockService.toogleStockCounterForProduct(-3, malt, type);
		} catch (StockException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(0, res.getCpt_value(), 0.1);

	}

	@Test(expected = StockException.class)
	public void shouldToogleStockCounterForProductThrowException() {
		StockCounter res = null;

		IGenericDao<StockCounter> daoMock = Mockito.mock(StockDAOImpl.class);

		SimpleMalt malt = null;
		CounterType type = null;
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

	public void shouldProcessStockMotionsForUpdatingStockCounters() throws ServiceException {

		List<AbstractStockMotion> stockMotions = new ArrayList<AbstractStockMotion>();
		List<StockCounter> stockCounters = new ArrayList<StockCounter>();

		stockMotions.add(move4BagsOfMaltForFab());
		stockMotions.add(move100gHopToFab());
		
		stockCounters = specStockService.processStockMotionsForUpdatingStockCounters(stockMotions);
		
		
		for (StockCounter stick : stockCounters){
			
			
			if (stick.getCpt_counter_type().equals(CounterType.STOCK_DISPO_FAB)){
				Assert.assertTrue(stick instanceof RawMaterialCounter);
				if(((RawMaterialCounter) stick).getCpt_product().getIng_fournisseur().equals("Weyermann")){
					
					Assert.assertTrue(stick.getCpt_value() == 2);
					
				}
			}
		}
		

	}

	private RawMaterialStockMotion move100gHopToFab() {
		RawMaterialStockMotion stm2 = new RawMaterialStockMotion();
		stm2.setStm_counter_from(CounterType.STOCK_DISPO_FAB);
		stm2.setStm_counter_to(CounterType.STOCK_EN_FAB);

		stm2.setStm_unit(StockUnit.GRAMME);
		stm2.setStr_product(houblon);

		return null;
	}

	private RawMaterialStockMotion move4BagsOfMaltForFab() {
		RawMaterialStockMotion stm1 = new RawMaterialStockMotion();

		stm1.setStm_counter_from(CounterType.STOCK_DISPO_FAB);
		stm1.setStm_counter_to(CounterType.STOCK_EN_FAB);

		stm1.setStr_product(malt);
		stm1.setStm_unit(StockUnit.SAC_25_KG);
		stm1.setStm_value(4);

		return stm1;
	}

	private void buildDataset(SimpleMalt malt, CounterType type) {

		
	}
}
