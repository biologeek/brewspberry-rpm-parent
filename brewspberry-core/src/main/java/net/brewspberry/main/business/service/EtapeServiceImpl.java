package net.brewspberry.main.business.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificEtapeService;
import net.brewspberry.main.business.ISpecificStockService;
import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.parser.Parser;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.DateManipulator;
import net.brewspberry.main.util.LogManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class EtapeServiceImpl implements IGenericService<Etape>, ISpecificEtapeService {

	@Autowired
	@Qualifier("etapeDaoImpl")
	IGenericDao<Etape> etapeDao;

	@Autowired
	@Qualifier("actionerServiceImpl")
	IGenericService<Actioner> genActionerService;

	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CounterType> counterTypeService;

	@Autowired
	Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> stepParserForRawMaterial;

	@Autowired
	public ISpecificStockService specStockService;

	private List<CounterType> counterTypeList;

	
	@Value("${param.stock.delay.limitToStockInFab.minutes}")
	String paramStockDelayLimitToStockInFabMinutes;

	private static Logger logger;
	
	
	public EtapeServiceImpl() {
		
		logger = LogManager.getInstance(this.getClass().getName());

	}

	@Override
	public Etape save(Etape arg0) throws Exception {

		arg0.setEtp_creation_date(new Date());

		Etape saved = etapeDao.save(arg0);

		if (arg0.hasActioners()) {
			for (Actioner actioner : arg0.getEtp_actioner()) {
				actioner.setAct_etape(arg0);
				genActionerService.save(actioner);
			}
		}
		return saved;
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

		}

		this.update(etape);
		return etape;
	}

	@Override
	public Etape getElementByName(String name) throws ServiceException {

		return null;
	}

	@Override
	/**
	 * Method called when step really starts step : existing step to start
	 * 
	 * @return step after update
	 */
	public Etape startStepForReal(Etape step) throws BusinessException {
		
		

		counterTypeList = getCounterTypeList();
		float delay = Float.parseFloat(paramStockDelayLimitToStockInFabMinutes);

		Etape oldStepInDatabase = this.getElementById(step.getEtp_id());

		
		logger.info("Starting step "+oldStepInDatabase.getEtp_id()+ ", brew "+oldStepInDatabase.getEtp_brassin().getBra_id());
		CounterType counterTypeFrom = null;

		// Starting step
		if (!oldStepInDatabase.isEtp_active()) {
			oldStepInDatabase.setEtp_debut_reel(new Date());
			oldStepInDatabase.setEtp_active(true);
			
			/*
			 * Calculates theoretical end date with given duration   
			 */
			oldStepInDatabase.setEtp_fin(DateManipulator.addTimeToDate(step.getEtp_debut_reel(), step.getEtp_duree()));
			

			// Updating date and persisting
			oldStepInDatabase.setEtp_update_date(new Date());

			etapeDao.update(oldStepInDatabase);
		} else {
			throw new BusinessException("Step is already started");
		}

		if (DateManipulator.getInstance().getDateFromDateAndDelay(oldStepInDatabase.getEtp_creation_date(), delay, "MINUTES")
				.before(oldStepInDatabase.getEtp_debut_reel())) {

			counterTypeFrom = CounterTypeConstants.STOCK_RESERVE_FAB.toDBCouter(counterTypeList);

		} else {

			counterTypeFrom = CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(counterTypeList);

		}

		/*
		 * Setting stocks to stock IN FAB when starting step
		 */

		specStockService.compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(oldStepInDatabase, step,
				counterTypeFrom, CounterTypeConstants.STOCK_EN_FAB.toDBCouter(counterTypeList));


		return oldStepInDatabase;
	}

	private List<CounterType> getCounterTypeList() {
		counterTypeList = counterTypeService.getAllElements();

		return counterTypeList;
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

		if (step != null && step.isEtp_active()) {

			counterTypeList = getCounterTypeList();

			CounterType counterTypeFrom = CounterTypeConstants.STOCK_EN_FAB.toDBCouter(counterTypeList);

			CounterType counterTypeTo = CounterTypeConstants.NONE.toDBCouter(counterTypeList);

			// First : updating step end date :

			step.setEtp_fin_reel(new Date());

			step.setEtp_update_date(new Date());

			etapeDao.update(step);
		}
		return step;
	}

	public IGenericDao<Etape> getEtapeDao() {
		return etapeDao;
	}

	public void setEtapeDao(IGenericDao<Etape> etapeDao) {
		this.etapeDao = etapeDao;
	}

	public IGenericService<Actioner> getGenActionerService() {
		return genActionerService;
	}

	public void setGenActionerService(IGenericService<Actioner> genActionerService) {
		this.genActionerService = genActionerService;
	}

	public IGenericService<CounterType> getCounterTypeService() {
		return counterTypeService;
	}

	public void setCounterTypeService(IGenericService<CounterType> counterTypeService) {
		this.counterTypeService = counterTypeService;
	}

	public Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> getStepParserForRawMaterial() {
		return stepParserForRawMaterial;
	}

	public void setStepParserForRawMaterial(
			Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> stepParserForRawMaterial) {
		this.stepParserForRawMaterial = stepParserForRawMaterial;
	}

	public ISpecificStockService getSpecStockService() {
		return specStockService;
	}

	public void setSpecStockService(ISpecificStockService specStockService) {
		this.specStockService = specStockService;
	}

	public String getParamStockDelayLimitToStockInFabMinutes() {
		return paramStockDelayLimitToStockInFabMinutes;
	}

	public void setParamStockDelayLimitToStockInFabMinutes(String paramStockDelayLimitToStockInFabMinutes) {
		this.paramStockDelayLimitToStockInFabMinutes = paramStockDelayLimitToStockInFabMinutes;
	}

}
