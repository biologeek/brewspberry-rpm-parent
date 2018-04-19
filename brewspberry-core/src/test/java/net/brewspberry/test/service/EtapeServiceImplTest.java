package net.brewspberry.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.brewspberry.main.business.ISpecificEtapeService;
import net.brewspberry.main.business.ISpecificStockService;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.DurationBO;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.service.CompteurTypeServiceImpl;
import net.brewspberry.main.business.service.EtapeServiceImpl;
import net.brewspberry.main.business.service.StockServiceImpl;
import net.brewspberry.main.dao.EtapeDaoImpl;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.DateManipulator;
import net.brewspberry.test.examples.IngredientExemple;
import net.brewspberry.test.examples.ProductExemple;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
@PrepareForTest({ ConfigLoader.class, EtapeServiceImpl.class })
public class EtapeServiceImplTest {

	@Spy
	EtapeServiceImpl sut = new EtapeServiceImpl();

	Etape etape;
	Calendar calCrea, calBegin, calEnd;

	@Mock
	private StockServiceImpl specStockService;

	@Mock
	EtapeDaoImpl etapeDaoMock;


	@Mock
	CompteurTypeServiceImpl genCounterTypeService;

	private List<CounterType> list;

	@Before
	public void init() throws ServiceException {

		MockitoAnnotations.initMocks(this);
	
		
		sut.setEtapeDao(etapeDaoMock);
		sut.setCounterTypeService(genCounterTypeService);
		sut.specStockService = specStockService;
		
		sut.setParamStockDelayLimitToStockInFabMinutes("10");

		calCrea = Calendar.getInstance();
		calCrea.add(Calendar.HOUR, -1);

		calBegin = Calendar.getInstance();
		calBegin.add(Calendar.MINUTE, -10);

		calEnd = Calendar.getInstance();
		calEnd.add(Calendar.MINUTE, -10);


		etape = new Etape.Builder().etp_id(1L).etp_active(false).etp_debut(new Date()).build();

		etape.setEtp_creation_date(calCrea.getTime());
		etape.setEtp_debut(calBegin.getTime());
		etape.setEtp_fin(calEnd.getTime());
		etape.setEtp_duree(new DurationBO(5, Calendar.MINUTE));
		etape.setEtp_brassin(new Brassin().id(1L));

	}

	@Test
	public void shouldStartStepForReal() throws BusinessException {

		list = getList();
		PowerMockito.mockStatic(ConfigLoader.class);
		ISpecificEtapeService stepServiceSpy = (ISpecificEtapeService) PowerMockito.spy(sut);

		Calendar calExpected = Calendar.getInstance();
		Calendar calActual = Calendar.getInstance();

		

//		String param = "param.stock.delay.limitToStockInFab.minutes";
//		String path = Constants.CONFIG_PROPERTIES;
//		PowerMockito.when(ConfigLoader.getConfigByKey(path, param)).thenReturn("30");

		Mockito.when(genCounterTypeService.getAllElements()).thenReturn(getList());
		Mockito.when(etapeDaoMock.getElementById(Mockito.anyLong())).thenReturn(etape);
		Mockito.when(specStockService.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
			.thenReturn(listOfStockCounters());

		Etape result = sut.startStepForReal(etape);
		calActual.setTime(result.getEtp_debut_reel());

		Assert.assertEquals(calExpected.get(Calendar.DAY_OF_WEEK), calActual.get(Calendar.DAY_OF_WEEK));
		Assert.assertEquals(calExpected.get(Calendar.MONTH), calActual.get(Calendar.MONTH));
		Assert.assertEquals(calExpected.get(Calendar.YEAR), calActual.get(Calendar.YEAR));
		Assert.assertEquals(calExpected.get(Calendar.HOUR), calActual.get(Calendar.HOUR));
		Assert.assertEquals(calExpected.get(Calendar.MINUTE), calActual.get(Calendar.MINUTE));

	}

	private List<StockCounter> listOfStockCounters() {
		List<StockCounter> counters = new ArrayList<>();
		
		counters.add(new IngredientStockCounterBuilder()//
				.ingredient(IngredientExemple.aSimpleMalt())//
				.type(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list))
				.unit(StockUnit.KILO)
				.value(10)
				.build());
		
		return counters;
	}

	@Test
	public void shouldStopStepForReal() throws BusinessException {
		list = getList();

		ISpecificEtapeService stepServiceSpy = (ISpecificEtapeService) PowerMockito.spy(sut);

		CounterType counterTypeFrom = CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list);
		CounterType counterTypeTo = CounterTypeConstants.NONE.toDBCouter(list);
		List<StockCounter> mockResult = new ArrayList<StockCounter>();

		mockResult.add(add10KOfMalt());

		Mockito.when(specStockService.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(etape, null,
				counterTypeFrom, counterTypeTo)).thenReturn(mockResult);

		Mockito.when(genCounterTypeService.getAllElements()).thenReturn(getList());

		etape.setEtp_debut_reel(calBegin.getTime());
		etape.setEtp_fin(calEnd.getTime());

		Calendar calExpected = Calendar.getInstance();
		Calendar calActual = Calendar.getInstance();

		Mockito.when(etapeDaoMock.update(etape)).thenAnswer(new Answer<Etape>() {
		    @Override
		    public Etape answer(InvocationOnMock invocation) throws Throwable {
		      Object[] args = invocation.getArguments();
		      return (Etape) args[0];
		    }
		  });

		etape.setEtp_active(true);
		Date date = new Date();
		// TODO : mock service calls so that only step service is tested
		Etape result = stepServiceSpy.stopStepForReal(etape);

		calActual.setTime(result.getEtp_fin_reel());

		Assert.assertEquals(calExpected.get(Calendar.DAY_OF_WEEK), calActual.get(Calendar.DAY_OF_WEEK));
		Assert.assertEquals(calExpected.get(Calendar.MONTH), calActual.get(Calendar.MONTH));
		Assert.assertEquals(calExpected.get(Calendar.YEAR), calActual.get(Calendar.YEAR));
		Assert.assertEquals(calExpected.get(Calendar.HOUR), calActual.get(Calendar.HOUR));
		Assert.assertEquals(calExpected.get(Calendar.MINUTE), calActual.get(Calendar.MINUTE));

	}

	private StockCounter add10KOfMalt() {

		SimpleMalt malt = new SimpleMalt();
		malt.setStb_id(1);

		return new IngredientStockCounterBuilder().ingredient(malt)
				.type(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(getList())).unit(StockUnit.GRAMME).value(10000)
				.build();

	}

	private List<CounterType> getList() {
		List<CounterType> counters = new ArrayList<>();
		
		for (CounterTypeConstants val : CounterTypeConstants.values()){
			counters.add(new CounterType(val.getCty_id(), val.getCty_libelle()));			
		}
		return counters;
	}
}
