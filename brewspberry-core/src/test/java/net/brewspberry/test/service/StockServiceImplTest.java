package net.brewspberry.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
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
import net.brewspberry.business.beans.stock.CounterTypeConstants;
import net.brewspberry.business.beans.stock.MotionDirection;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.business.service.StockServiceImpl;
import net.brewspberry.dao.StockDAOImpl;
import net.brewspberry.test.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class StockServiceImplTest extends AbstractTest {

	@Autowired
	@Qualifier("stockServiceImpl")
	IGenericService<StockCounter> genStockService;

	@Mock
	ISpecificStockDao specDaoMock;

	@Mock
	private IGenericDao<StockCounter> genericDAO;

	@Autowired
	@Qualifier("compteurTypeDaoImpl")
	private IGenericDao<CounterType> genericCounterTypeDAO;

	@Mock
	private StockCounter cptToDecrease;

	@InjectMocks
	@Autowired
	ISpecificStockService specStockService;

	@Mock
	ISpecificStockService specStockServiceMock;

	StockCounter res = null;

	SimpleMalt malt;
	SimpleHoublon houblon;
	CounterTypeConstants type;
	List<CounterType> counterTypeList;
	
	
	StockServiceImpl stockServiceImpl = new StockServiceImpl();

	@Before
	public void init() {

		counterTypeList = genericCounterTypeDAO.getAllElements();
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
		houblon.setStb_id(2);
		houblon.setIng_desc("test2");
		houblon.setIng_fournisseur("Saveur Biere");
		houblon.setIng_unitary_price(20);
		houblon.setIng_unitary_price_unit(StockUnit.KILO);
		houblon.setIng_unitary_weight(0.1f);
		houblon.setIng_unitary_weight_unit(StockUnit.KILO);

		type = CounterTypeConstants.STOCK_RESERVE_FAB;

		StockCounter stl = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(type.toDBCouter(counterTypeList)).unit(StockUnit.KILO)
				.value(3)).ingredient(malt).build();

		StockCounter st2 = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(CounterTypeConstants.STOCK_DISPO_FAB
						.toDBCouter(counterTypeList)).unit(StockUnit.SAC_25_KG)
				.value(10)).ingredient(malt).build();

		StockCounter st3 = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(CounterTypeConstants.STOCK_DISPO_FAB
						.toDBCouter(counterTypeList)).unit(StockUnit.GRAMME)
				.value(200)).ingredient(houblon).build();

		CounterType typeDB = type.toDBCouter(counterTypeList);
		try {
			Mockito.when(
					specDaoMock.getStockCounterByProductAndType(malt, typeDB))
					.thenReturn(stl);

			Mockito.when(genericDAO.update(cptToDecrease)).thenReturn(stl);

		} catch (DAOException e) {
			
			e.printStackTrace();
		}

	}

	@Test
	public void shouldToogleStockCounterForProduct() {

		Assert.assertNotNull(specStockService);
		try {
			res = specStockService.toogleStockCounterForProduct(-3, malt,
					type.toDBCouter(counterTypeList));
		} catch (StockException | ServiceException e) {
			
			e.printStackTrace();
		}

		Assert.assertEquals(2, res.getCpt_value(), 0.1);

	}

	@Test(expected = StockException.class)
	public void shouldToogleStockCounterForProductThrowException()
			throws StockException, ServiceException {
		StockCounter res = null;

		CounterTypeConstants type = CounterTypeConstants.STOCK_DISPO_FAB;
		StockCounter maltStockCounter = (StockCounter) ((IngredientStockCounterBuilder) new IngredientStockCounterBuilder()
				.type(type.toDBCouter(counterTypeList)).unit(StockUnit.KILO)
				.value(1)).ingredient(malt).build();

		CounterType typeDB = type.toDBCouter(counterTypeList);
		try {
			ReflectionTestUtils.setField(specStockService, "specDAO",
					specDaoMock);
			Mockito.when(
					specDaoMock.getStockCounterByProductAndType(malt, typeDB))
					.thenReturn(maltStockCounter);
		} catch (DAOException e1) {
			
			e1.printStackTrace();
		}

		res = specStockService.toogleStockCounterForProduct(-2, malt,
				type.toDBCouter(counterTypeList));

	}

	@Test
	public void shouldProcessStockMotionsForUpdatingStockCounters()
			throws ServiceException {

		List<AbstractStockMotion> stockMotions = new ArrayList<AbstractStockMotion>();
		List<StockCounter> stockCounters = new ArrayList<StockCounter>();

		stockMotions.add(move4BagsOfMaltForFab());
		stockMotions.add(move100gHopToFab());

		List<StockCounter> builtMockStockCountersList = buildMockStockCountersList();
		Mockito.when(specStockServiceMock.getWholeStockForProduct(malt))
				.thenReturn(builtMockStockCountersList);

		stockCounters = specStockService
				.processStockMotionsForUpdatingStockCounters(stockMotions);
		Assert.assertNotNull(stockCounters);

		for (StockCounter stick : stockCounters) {

			Assert.assertNotNull(stick);
			if (stick.getCpt_counter_type().toConstant()
					.equals(CounterTypeConstants.STOCK_DISPO_FAB)) {

				Assert.assertTrue(stick instanceof RawMaterialCounter);

				if (((RawMaterialCounter) stick).getCpt_product()
						.getIng_fournisseur().equals("Weyermann")) {

					Assert.assertEquals(-100000, stick.getCpt_value(), 0.1);

				}
			}

			if (stick.getCpt_counter_type().toConstant()
					.equals(CounterTypeConstants.STOCK_RESERVE_FAB)) {
				Assert.assertTrue(stick instanceof RawMaterialCounter);

				if (((RawMaterialCounter) stick).getCpt_product()
						.getIng_fournisseur().equals("Biere Boutique")) {

					Assert.assertEquals(2, stick.getCpt_value(), 0);

				}
			}
		}

	}

	@Test
	public void shouldUpdateStockValueWithStockMotion() {

		RawMaterialCounter counter = new IngredientStockCounterBuilder().ingredient(malt).type(
				CounterTypeConstants.STOCK_DISPO_FAB
						.toDBCouter(counterTypeList)).value(6000).unit(StockUnit.GRAMME).build();
		
		RawMaterialStockMotion motion = new RawMaterialStockMotion();
		motion.setStm_counter_from(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(counterTypeList));
		motion.setStm_counter_to(CounterTypeConstants.STOCK_DEM_CASSE.toDBCouter(counterTypeList));
		motion.setStm_motion_date(new Date());
		motion.setStm_unit(StockUnit.SAC_1_KG);
		motion.setStm_value(2);
		
		
		
		StockCounter result = stockServiceImpl.updateStockValueWithStockMotion(counter, motion, MotionDirection.FROM);
		
		Assert.assertEquals(4000, result.getCpt_value(), 0.1);
	}

	@Test
	public void shouldCreateOrUpdateStockCounterWithStockValue() {

		RawMaterialCounter counter = new IngredientStockCounterBuilder().ingredient(malt).type(
				CounterTypeConstants.STOCK_DISPO_FAB
						.toDBCouter(counterTypeList)).value(6000).unit(StockUnit.GRAMME).build();
		
		RawMaterialStockMotion motion = new RawMaterialStockMotion();
		motion.setStm_counter_from(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(counterTypeList));
		motion.setStm_counter_to(CounterTypeConstants.STOCK_DEM_CASSE.toDBCouter(counterTypeList));
		motion.setStm_motion_date(new Date());
		motion.setStm_unit(StockUnit.SAC_1_KG);
		motion.setStm_value(2);
		
		try {
			stockServiceImpl.createOrUpdateStockCounterWithStockValue(motion, counter, MotionDirection.FROM);
		} catch (BusinessException e) {
			
			e.printStackTrace();
		}
		
		

	}

	private List<StockCounter> buildMockStockCountersList() {
		List<StockCounter> list = new ArrayList<StockCounter>();
		StockCounter stk1 = (StockCounter) new IngredientStockCounterBuilder()
				.ingredient(malt)
				.type(CounterTypeConstants.STOCK_DISPO_FAB
						.toDBCouter(counterTypeList)).unit(StockUnit.GRAMME)
				.value(20000).build();
		stk1.setCpt_id(1);

		list.add(stk1);

		return list;
	}

	private RawMaterialStockMotion move100gHopToFab() {
		RawMaterialStockMotion stm2 = new RawMaterialStockMotion();
		stm2.setStm_counter_from(CounterTypeConstants.STOCK_RESERVE_FAB
				.toDBCouter(counterTypeList));
		stm2.setStm_counter_to(CounterTypeConstants.STOCK_EN_FAB
				.toDBCouter(counterTypeList));

		stm2.setStm_unit(StockUnit.GRAMME);
		stm2.setStr_product(houblon);

		return stm2;
	}

	private RawMaterialStockMotion move4BagsOfMaltForFab() {
		RawMaterialStockMotion stm1 = new RawMaterialStockMotion();

		stm1.setStm_counter_from(CounterTypeConstants.STOCK_DISPO_FAB
				.toDBCouter(counterTypeList));
		stm1.setStm_counter_to(CounterTypeConstants.STOCK_EN_FAB
				.toDBCouter(counterTypeList));

		stm1.setStr_product(malt);
		stm1.setStm_unit(StockUnit.SAC_25_KG);
		stm1.setStm_value(4);

		return stm1;
	}

	private void buildDataset(SimpleMalt malt, CounterTypeConstants type) {

	}
}
