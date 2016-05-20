package net.brewspberry.test.service;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.Session.LockRequest;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.stat.SessionStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.service.HopServiceImpl;
import net.brewspberry.business.service.MaltServiceImpl;
import net.brewspberry.business.service.YeastServiceImpl;
import net.brewspberry.dao.HopDaoImpl;
import net.brewspberry.dao.SimpleHopDaoImpl;
import net.brewspberry.util.HibernateUtil;
import net.brewspberry.util.LogManager;

@PrepareForTest({ISpecificIngredientService.class, HibernateUtil.class, LogManager.class})
@RunWith(PowerMockRunner.class)
public class IngredientsServiceTest {

	ISpecificIngredientService ingService;

	HopDaoImpl ingDAO;
	IGenericDao<SimpleHoublon> shopDao;

	private SimpleHopDaoImpl singDAO;
	
	
	static String[] arrayMal;
	static String[] arrayMalQte;
	static String[] arrayMalPrix;

	static String[] arrayHop;
	static String[] arrayHopQte;
	static String[] arrayHopPrix;

	static String[] arrayYeast;
	static String[] arrayYeastQte;
	static String[] arrayYeastPrix;

	static Houblon testHop;
	static Malt testMalt;
	static Levure testYeast;

	@BeforeClass
	public static void setUpTestData() {

		arrayMal = new String[] { "0", "1" };
		arrayMalQte = new String[] { "12", "21" };
		arrayMalPrix = new String[] { "1.2", "2.1" };

		testMalt = Mockito.mock(Malt.class);

		arrayHop = new String[] { "2", "3" };
		arrayHopQte = new String[] { "34", "43" };
		arrayHopPrix = new String[] { "3.4", "4.3" };

		testHop = Mockito.mock(Houblon.class);

		arrayYeast = new String[] { "4", "5" };
		arrayYeastQte = new String[] { "45", "54" };
		arrayYeastPrix = new String[] { "4.5", "5.4" };

		testYeast = Mockito.mock(Levure.class);

	}

	@Before
	public void beforeTest() {

	}

	@Test
	public void notRealTest(){
		
	}
	
	//@Test
	public void shouldReturnIngredientFromHopArrayId() throws NoSuchMethodException, SecurityException {

		PowerMockito.mockStatic(LogManager.class);
		PowerMockito.mockStatic(HibernateUtil.class);
		
		ingDAO = Mockito.mock(HopDaoImpl.class);
		singDAO = Mockito.mock(SimpleHopDaoImpl.class);

		ingService = new HopServiceImpl();
		PowerMockito.spy(HopServiceImpl.class);

		mockHibernateSession();
		

		//Mockito.when(new HopDaoImpl()).thenReturn(ingDAO);
		//Mockito.when(new SimpleHopDaoImpl()).thenReturn(singDAO);
		

		PowerMockito.when(LogManager.getInstance(HopServiceImpl.class.getName()))
		.thenReturn(Logger.getAnonymousLogger());
		PowerMockito.when(LogManager.getInstance(HibernateUtil.class.getName()))
		.thenReturn(Logger.getGlobal());

		
		
		Mockito.when(singDAO.getElementById(0)).thenReturn(testHop);
		Mockito.when(singDAO.getElementById(1)).thenReturn(testHop);
		
		
		List<Houblon> hopList = ingService.getIngredientFromArrayId(arrayHop, arrayHopQte, arrayHopPrix);


		Assert.assertEquals(2, hopList.size());
		Assert.assertEquals(12, hopList.get(0).getIng_quantite());
		Assert.assertEquals(2.1, hopList.get(1).getIng_prix());

		
	}

	
	public void mockHibernateSession(){
		
		SessionFactory mockedSessFact = Mockito.mock(SessionFactory.class);
		Session mockedSession = Mockito.mock(Session.class);
		PowerMockito.when(HibernateUtil.configureSessionFactory()).thenReturn(mockedSessFact);
		PowerMockito.when(HibernateUtil.getSession()).thenReturn(mockedSession);
	}
	
	private void getMockedVoidSession() {
		PowerMockito.when(HibernateUtil.getSession()).thenReturn(new Session() {
			
			@Override
			public Transaction getTransaction() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getTenantIdentifier() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query getNamedQuery(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ProcedureCall getNamedProcedureCall(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ProcedureCall createStoredProcedureCall(String arg0, String... arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ProcedureCall createStoredProcedureCall(String arg0, Class... arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ProcedureCall createStoredProcedureCall(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SQLQuery createSQLQuery(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query createQuery(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Criteria createCriteria(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Criteria createCriteria(Class arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Criteria createCriteria(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Criteria createCriteria(Class arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Transaction beginTransaction() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void update(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void update(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setReadOnly(Object arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFlushMode(FlushMode arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setDefaultReadOnly(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setCacheMode(CacheMode arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public SharedSessionBuilder sessionWithOptions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void saveOrUpdate(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void saveOrUpdate(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Serializable save(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Serializable save(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void replicate(String arg0, Object arg1, ReplicationMode arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void replicate(Object arg0, ReplicationMode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(String arg0, Object arg1, LockOptions arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(Object arg0, LockOptions arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(Object arg0, LockMode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refresh(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reconnect(Connection arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void persist(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void persist(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object merge(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object merge(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void lock(String arg0, Object arg1, LockMode arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void lock(Object arg0, LockMode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object load(String arg0, Serializable arg1, LockOptions arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object load(String arg0, Serializable arg1, LockMode arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object load(Class arg0, Serializable arg1, LockOptions arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object load(Class arg0, Serializable arg1, LockMode arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void load(Object arg0, Serializable arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object load(String arg0, Serializable arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object load(Class arg0, Serializable arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isReadOnly(Object arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isOpen() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isFetchProfileEnabled(String arg0) throws UnknownProfileException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDirty() throws HibernateException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isDefaultReadOnly() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isConnected() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public TypeHelper getTypeHelper() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SessionStatistics getStatistics() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SessionFactory getSessionFactory() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LobHelper getLobHelper() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Serializable getIdentifier(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FlushMode getFlushMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getEntityName(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Filter getEnabledFilter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LockMode getCurrentLockMode(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public CacheMode getCacheMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(String arg0, Serializable arg1, LockOptions arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(String arg0, Serializable arg1, LockMode arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(Class arg0, Serializable arg1, LockOptions arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(Class arg0, Serializable arg1, LockMode arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(String arg0, Serializable arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object get(Class arg0, Serializable arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void flush() throws HibernateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void evict(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Filter enableFilter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void enableFetchProfile(String arg0) throws UnknownProfileException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void doWork(Work arg0) throws HibernateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public <T> T doReturningWork(ReturningWork<T> arg0) throws HibernateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Connection disconnect() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void disableFilter(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void disableFetchProfile(String arg0) throws UnknownProfileException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Query createFilter(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean contains(Object arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Connection close() throws HibernateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelQuery() throws HibernateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public SimpleNaturalIdLoadAccess bySimpleNaturalId(Class arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SimpleNaturalIdLoadAccess bySimpleNaturalId(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public NaturalIdLoadAccess byNaturalId(Class arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public NaturalIdLoadAccess byNaturalId(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IdentifierLoadAccess byId(Class arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IdentifierLoadAccess byId(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public LockRequest buildLockRequest(LockOptions arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addEventListeners(SessionEventListener... arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
