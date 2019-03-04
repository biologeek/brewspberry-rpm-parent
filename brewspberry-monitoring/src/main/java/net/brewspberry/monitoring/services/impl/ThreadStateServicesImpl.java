package net.brewspberry.monitoring.services.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.ThreadState;
import net.brewspberry.monitoring.model.ThreadWitness;
import net.brewspberry.monitoring.repositories.ThreadWitnessRepository;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessCheckServices;
import net.brewspberry.monitoring.services.ThreadWitnessServices;

@Service
public class ThreadStateServicesImpl implements ThreadStateServices, ThreadWitnessServices, ThreadWitnessCheckServices {

	private ThreadWitnessRepository threadWitnessRepository;

	
	public ThreadStateServicesImpl() {
		super();
	}

	@Override
	public ThreadState readState(String sensorUuid) throws TechnicalException {
		return em.find(ThreadState.class, sensorUuid);
	}

	@Override
	public List<ThreadState> readStates() throws TechnicalException {
		return em.createQuery("from ThreadState").getResultList();
	}

	@Override
	public void writeState(ThreadState state) throws TechnicalException {
		Assert.notNull(state, "ThreadState is null");
		em.persist(state);
	}

	@Override
	public void cleanState(String uuid) throws TechnicalException {
		ThreadState entity = readState(uuid);
		if (entity == null)
			throw new TechnicalException("No thread running for " + uuid);
		em.remove(entity);
	}

	@Override
	public void cleanState(List<String> collect) throws TechnicalException {
		for (String t : collect) {
			cleanState(t);
		}
	}

	@Override
	public ThreadWitness checkWitness(String uuid) {
		return em.find(ThreadWitness.class, uuid);
	}

	@Override
	public void witnessThreadStart(String uuid) throws TechnicalException {
		ThreadWitness witness = em.find(ThreadWitness.class, uuid);
		if (witness == null) {
			ThreadWitness wit = new ThreadWitness()//
					.uuid(uuid)//
					.date(new Date());
			em.persist(wit);
		} else {
			throw new TechnicalException("thread.started");
		}
	}

	@Override
	public void witnessThreadinterrupt(String uuid) {
		ThreadWitness entity = em.find(ThreadWitness.class, uuid);
		em.remove(entity);
	}
}