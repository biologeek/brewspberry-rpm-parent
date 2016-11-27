package net.brewspberry.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.Malt;
import net.brewspberry.main.business.beans.SimpleMalt;
import net.brewspberry.main.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.parser.Parser;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
@PrepareForTest(ConfigLoader.class)
public class RawMaterialStockCounterParserForStepTest {

	@Autowired
	private Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> parser;
	private List<CounterType> list;
	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	private IGenericService<CounterType> counterTypeService;


	Etape etp1, etp2;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);
		etp1 = new Etape();
		etp2 = new Etape();
		SimpleMalt sMalt = new SimpleMalt();
		Malt malt, malt2;

		list = counterTypeService.getAllElements();

		sMalt.setStb_id(25);
		sMalt.setIng_desc("test");
		sMalt.setIng_disc("m");
		sMalt.setIng_fournisseur("testFou");

		malt2 = new Malt(sMalt);
		malt2.setIng_prix(10);
		malt2.setIng_quantite(12);


		malt = new Malt(sMalt);
		malt.setIng_prix(10);
		malt.setIng_quantite(5);
		RawMaterialCounter counter1 = new IngredientStockCounterBuilder()
				.type(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list)).ingredient(sMalt).unit(StockUnit.KILO)
				.value(10).build();

		List<Malt> etp_malts1 = new ArrayList<Malt>();
		List<Malt> etp_malts2 = new ArrayList<Malt>();
		etp_malts1.add(malt);
		etp_malts2.add(malt2);

		etp1.setEtp_malts(new HashSet<Malt>(etp_malts1));
		etp2.setEtp_malts(new HashSet<Malt>(etp_malts2));

		etp1.setEtp_debut(new Date());
		etp2.setEtp_debut(new Date());

	}

	@Test
	public void shouldCompareTwoObjectsAndExtractStockMotionsWhenStepBegins() {

		List<RawMaterialStockMotion> motions = parser.compareTwoObjectsAndExtractStockMotions(null, etp1,
				CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list));

		Assert.assertEquals(1L, motions.size());

		Assert.assertEquals(5, motions.get(0).getStm_value(), 0.1);
		Assert.assertEquals(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list), motions.get(0).getStm_counter_to());
		Assert.assertEquals(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list),
				motions.get(0).getStm_counter_from());

	}

	@Test
	public void shouldCompareTwoObjectsAndExtractStockMotionsWhenStepEnds() {

		List<RawMaterialStockMotion> motions = parser.compareTwoObjectsAndExtractStockMotions(etp1, null,
				CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list));

		Assert.assertEquals(1L, motions.size());

		Assert.assertEquals(5, motions.get(0).getStm_value(), 0.1);
		Assert.assertEquals(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list), motions.get(0).getStm_counter_from());
		Assert.assertEquals(CounterTypeConstants.NONE.toDBCouter(list), motions.get(0).getStm_counter_to());

	}

	@Test
	public void shouldCompareTwoObjectsAndExtractStockMotionsWhenStepsNotNull() {

		
		PowerMockito.mockStatic(ConfigLoader.class);

		Mockito.when(
				ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.stock.delay.limitToStockInFab.minutes"))
				.thenReturn("30");

		List<RawMaterialStockMotion> motions = parser.compareTwoObjectsAndExtractStockMotions(etp1, etp2,
				CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list));

		Assert.assertEquals(1L, motions.size());

		Assert.assertEquals(7, motions.get(0).getStm_value(), 0.1);
		Assert.assertEquals(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list),
				motions.get(0).getStm_counter_from());
		Assert.assertEquals(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list), motions.get(0).getStm_counter_to());
	}
}
