package net.brewspberry.test.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.CounterTypeConstants;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.parser.Parser;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class EtapeServiceImplTest {

	@Autowired
	ISpecificEtapeService specEtapeService;

	Etape etape;
	Calendar calCrea, calBegin, calEnd;

	@Mock
	@Autowired
	private ISpecificStockService specStockService;

	private Etape newEtape;

	private Etape oldEtape;

	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	private IGenericService<CounterType> genCounterTypeService;

	@Before
	public void init() {

		calCrea = Calendar.getInstance();
		calCrea.add(Calendar.HOUR, -1);

		calBegin = Calendar.getInstance();
		calBegin.add(Calendar.MINUTE, -10);

		calEnd = Calendar.getInstance();
		calEnd.add(Calendar.MINUTE, -10);

		etape = new Etape();

		newEtape = new Etape();

		etape.setEtp_creation_date(calCrea.getTime());
		etape.setEtp_debut(calBegin.getTime());
		etape.setEtp_fin(calEnd.getTime());

	}

	@Test
	public void shouldStopStepForReal() {

		List<CounterType> list = getList();
		Mockito.when(specStockService
				.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(
						oldEtape, newEtape,
						CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list),
						CounterTypeConstants.NONE.toDBCouter(list)));

		etape.setEtp_debut_reel(calBegin.getTime());
		etape.setEtp_fin(calEnd.getTime());

		Calendar calExpected = Calendar.getInstance();
		Calendar calActual = Calendar.getInstance();

		Date date = new Date();
		// TODO : mock service calls so that only step service is tested
		Etape result = specEtapeService.stopStepForReal(etape);
		calActual.setTime(result.getEtp_fin_reel());

		Assert.assertEquals(calExpected.get(Calendar.DAY_OF_WEEK),
				calActual.get(Calendar.DAY_OF_WEEK));
		Assert.assertEquals(calExpected.get(Calendar.MONTH),
				calActual.get(Calendar.MONTH));
		Assert.assertEquals(calExpected.get(Calendar.YEAR),
				calActual.get(Calendar.YEAR));
		Assert.assertEquals(calExpected.get(Calendar.HOUR),
				calActual.get(Calendar.HOUR));
		Assert.assertEquals(calExpected.get(Calendar.MINUTE),
				calActual.get(Calendar.MINUTE));

	}

	private List<CounterType> getList() {
		// TODO Auto-generated method stub
		return genCounterTypeService.getAllElements();
	}

	@Test
	public void shouldStartStepForReal() throws BusinessException {

		Calendar calExpected = Calendar.getInstance();
		Calendar calActual = Calendar.getInstance();

		Date date = new Date();
		Etape result = specEtapeService.startStepForReal(etape);
		calActual.setTime(result.getEtp_fin_reel());

		Assert.assertEquals(calExpected.get(Calendar.DAY_OF_WEEK),
				calActual.get(Calendar.DAY_OF_WEEK));
		Assert.assertEquals(calExpected.get(Calendar.MONTH),
				calActual.get(Calendar.MONTH));
		Assert.assertEquals(calExpected.get(Calendar.YEAR),
				calActual.get(Calendar.YEAR));
		Assert.assertEquals(calExpected.get(Calendar.HOUR),
				calActual.get(Calendar.HOUR));
		Assert.assertEquals(calExpected.get(Calendar.MINUTE),
				calActual.get(Calendar.MINUTE));

	}

}
