package net.brewspberry.business.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.CounterTypeConstants;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.parser.Parser;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.DateManipulator;

@Service
@Transactional
public class EtapeServiceImpl implements IGenericService<Etape>, ISpecificEtapeService {

	@Autowired
	@Qualifier("etapeDaoImpl")
	IGenericDao<Etape> etapeDao;

	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CounterType> counterTypeService;

	@Autowired
	Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> stepParserForRawMaterial;

	@Autowired
	public ISpecificStockService specStockService;

	private List<CounterType> list;

	public EtapeServiceImpl() {

	}

	@Override
	public Etape save(Etape arg0) throws Exception {

		return etapeDao.save(arg0);
	}

	@Override
	public Etape update(Etape arg0) {

		return etapeDao.update(arg0);
	}

	@Override
	public Etape getElementById(long id) {
		return etapeDao.getElementById(id);
	}

	@Override
	public List<Etape> getAllElements() {
		return etapeDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		etapeDao.deleteElement(id);

	}

	@Override
	public void deleteElement(Etape arg0) {
		etapeDao.deleteElement(arg0);
	}

	@Override
	public List<Etape> getAllDistinctElements() {
		return etapeDao.getAllDistinctElements();
	}

	@Override
	public Etape terminateStep(Etape etape) {

		if (etape != null) {

			etape.setEtp_fin(new Date());

			etape.setEtp_duree(
					DateManipulator.getInstance().getDurationBetween(etape.getEtp_debut(), etape.getEtp_fin()));

		}

		this.update(etape);
		return etape;
	}

	@Override
	public Etape getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Method called when step really starts step : existing step to start
	 * 
	 * @return step after update
	 */
	public Etape startStepForReal(Etape step) throws BusinessException {

		list = getList();
		float delay = Float.parseFloat(ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES,
				"param.stock.delay.limitToStockInFab.minutes"));

		Etape oldStepInDatabase = this.getElementById(step.getEtp_id());

		CounterType counterTypeFrom = null;

		// Starting step
		if (step.getEtp_debut_reel() == null) {
			step.setEtp_debut_reel(new Date());
		} else {
			throw new BusinessException("Step is already started");
		}

		/*
		 * If step was created at least X minutes before real beginning, stock
		 * has been reserved
		 * 
		 * Else, stock was already put in fab
		 */
		try {
			if (DateManipulator.getInstance().getDateFromDateAndDelay(step.getEtp_creation_date(), delay, "MINUTES")
					.before(step.getEtp_debut_reel())) {

				counterTypeFrom = counterTypeService
						.getElementByName(CounterTypeConstants.STOCK_RESERVE_FAB.getCty_libelle());

			} else {

				counterTypeFrom = counterTypeService
						.getElementByName(CounterTypeConstants.STOCK_DISPO_FAB.getCty_libelle());

			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		/*
		 * Setting stocks to stock IN FAB when starting step
		 */

		specStockService.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(oldStepInDatabase, step,
				counterTypeFrom, CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list));

		// Updating date and persisting
		step.setEtp_update_date(new Date());

		return etapeDao.update(step);
	}

	private List<CounterType> getList() {
		list = counterTypeService.getAllElements();

		return list;
	}

	@Override
	/**
	 * Method used to stop a step when it's finished. As step is finished stock
	 * must be updated
	 * 
	 * 
	 * step step to finish
	 * 
	 * @return step after update
	 */
	public Etape stopStepForReal(Etape step) {

		List<StockCounter> countersFromList = null;
		list = getList();

		CounterType counterTypeFrom = CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list);

		CounterType counterTypeTo = CounterTypeConstants.NONE.toDBCouter(list);

		// First : updating step end date :

		step.setEtp_fin_reel(new Date());

		step.setEtp_update_date(new Date());

		// Updating stock counters from step. Null step will decrement stocks
		countersFromList = specStockService
				.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(step, null, counterTypeFrom,
						counterTypeTo);

		return etapeDao.update(step);
	}
}
