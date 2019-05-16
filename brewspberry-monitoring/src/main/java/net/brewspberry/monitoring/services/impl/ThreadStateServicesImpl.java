package net.brewspberry.monitoring.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.ThreadState;
import net.brewspberry.monitoring.model.ThreadWitness;
import net.brewspberry.monitoring.repositories.ThreadStateRepository;
import net.brewspberry.monitoring.repositories.ThreadWitnessRepository;
import net.brewspberry.monitoring.services.CommonDeviceService;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessCheckServices;
import net.brewspberry.monitoring.services.ThreadWitnessServices;

@Service
public class ThreadStateServicesImpl implements ThreadStateServices, ThreadWitnessServices, ThreadWitnessCheckServices {

	@Autowired
	private ThreadWitnessRepository threadWitnessRepository;
	@Autowired
	private ThreadStateRepository threadStateRepository;
	@Autowired
	private CommonDeviceService abstractDeviceService;

	
	public ThreadStateServicesImpl() {
		super();
	}

	@Override
	public ThreadState readState(String sensorUuid) throws TechnicalException {
		return threadStateRepository.findByUuid(sensorUuid);
	}

	@Override
	public List<ThreadState> readStates() throws TechnicalException {
		return threadStateRepository.findAll();
	}

	@Override
	public void writeState(ThreadState state) throws TechnicalException {
		Assert.notNull(state, "ThreadState is null");
		threadStateRepository.save(state);
	}

	@Override
	public void cleanState(String uuid) {
		ThreadState entity = readState(uuid);
		if (entity == null)
			return ;
		threadStateRepository.delete(entity);
		try {
			abstractDeviceService.stopDevice(uuid);
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
		} catch (StateChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void cleanState(List<String> uuids) throws TechnicalException {
		for (String t : uuids) {
			cleanState(t);
		}
	}

	@Override
	public ThreadWitness checkWitness(String uuid) {
		return threadWitnessRepository.findByUuid(uuid);
	}

	@Override
	public void witnessThreadStart(String uuid) throws TechnicalException {
		ThreadWitness witness = threadWitnessRepository.findByUuid(uuid);
		if (witness == null) {
			ThreadWitness wit = new ThreadWitness()//
					.uuid(uuid)//
					.date(new Date());
			try {
				threadWitnessRepository.save(wit);
			} catch (NestedRuntimeException e) {
				e.printStackTrace();
				throw new TechnicalException("thread.db_exception");
			}
		} else {
			throw new IllegalAccessError("thread.already_executing");
		}
	}

	@Override
	public void witnessThreadInterrupt(String uuid) {
		ThreadWitness entity = threadWitnessRepository.findByUuid(uuid);
		threadWitnessRepository.delete(entity);
	}
}