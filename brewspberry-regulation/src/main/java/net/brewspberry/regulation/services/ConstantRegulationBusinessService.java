package net.brewspberry.regulation.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.regulation.clients.MonitoringClient;
import net.brewspberry.regulation.model.ConstantRegulationModel;

@Service
public class ConstantRegulationBusinessService implements RegulationBusinessService<ConstantRegulationModel> {

	@Autowired
	private MonitoringClient monitoringClient;

	public void launchRegulation(ConstantRegulationModel model) {
		
		Assert.notNull(model, "regulation.model_null");
		Assert.isTrue(model.getDuration() > 0 || model.getEnd() != null, "regulation.no_duration_or_end");
		Assert.notNull(model.getInstruction(), "regulation.instruction_null");
		
		LocalDateTime begin = LocalDateTime.now();
		LocalDateTime end = model.getEnd() != null ? model.getEnd() : begin.plus(Duration.of(model.getDuration(), ChronoUnit.MILLIS));
		
		
		while (LocalDateTime.now().isBefore(end)) {
			retrieveTemperatures();
		}
	}

	private List<Temperature> retrieveTemperatures(ConstantRegulationModel model) {
		// TODO Auto-generated method stub
		this.monitoringClient.temperature().getTemperaturesForSensors(model.getUuids());
	}

}
