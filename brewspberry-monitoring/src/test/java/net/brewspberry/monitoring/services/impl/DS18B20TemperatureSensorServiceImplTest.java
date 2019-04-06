package net.brewspberry.monitoring.services.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.pi4j.io.w1.W1BaseDevice;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import ch.qos.logback.core.util.Duration;
import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureSensorRepository;
import net.brewspberry.monitoring.services.ThreadWitnessServices;

@RunWith(MockitoJUnitRunner.class)
public class DS18B20TemperatureSensorServiceImplTest {

	@Mock
	W1Master w1Master;

	@Mock
	TemperatureSensorRepository sensorRepo;

	@Spy
	ThreadStateServicesImpl threadStateServices;
	@Mock
	ThreadWitnessServices threadWitnessServices;
	
	@InjectMocks
	DS18B20TemperatureSensorServicesImpl sut;

	public void init() {
		sut = new DS18B20TemperatureSensorServicesImpl();		
		sut.setOneWireMaster(w1Master);
		sut.threadWitnessService = threadStateServices;
		sut.threadStateService = threadStateServices;
	}

	@Test
	public void shouldGetTemperatureForDevice_givenTwoDevicesPlugged() throws Exception {
		List<W1Device> devs = aListOf2Devices("28-123456789", "28-12121212");
		when(w1Master.getDevices(0x28)).thenReturn(devs);
		TemperatureSensor sensor = new TemperatureSensor();
		sensor.setId(1L);
		sensor.setUuid("28-123456789");
		
		TemperatureMeasurement temperature = sut.getTemperatureForDevice(sensor);
		Assert.assertEquals(2.0f, temperature.getTemperature(), 0.1);
		Assert.assertEquals("28-123456789", temperature.getSensor().getUuid());
	}

	@Test(expected=DeviceNotFoundException.class)
	public void shouldGetTemperatureForDevice_givenNoDevicesPlugged() throws Exception {
		
		when(w1Master.getDevices(0x28)).thenReturn(new ArrayList<>());
		TemperatureSensor sensor = new TemperatureSensor();
		sensor.setId(1L);
		sensor.setUuid("28-12345678");
		
		sut.getTemperatureForDevice(sensor);
	}

	@Test
	public void shouldRunRegularTemperatureMeasurement() throws Exception {
		Map<String, Object> map = parametersForxMinEveryySeconds(0.5, 3);
		map.put(TemperatureSensor.DEVICE_LIST, aListOf1Sensor("28-1111111111"));
		List<W1Device> aListOf2Devices = aListOf2Devices("28-1111111111", "28-1111111112");
		when(w1Master.getDevices(0x28)).thenReturn(aListOf2Devices);
		sut.runRegularTemperatureMeasurement(aListOf1Sensor("28-1111111111"), map);
		
		verify(threadWitnessServices).witnessThreadStart(Mockito.anyString());
		
	}
	
	private Map<String, Object> parametersForxMinEveryySeconds(double d, int i) {
		Map<String, Object> map = new HashMap<>();
		map.put(TemperatureSensor.DURATION, Duration.buildByMinutes(d));
		map.put(TemperatureSensor.FREQUENCY, i*1000);
		return map;
	}

	private List<W1Device> aListOf2Devices(String... string) throws IOException {
		W1BaseDevice dev1 = mock(W1BaseDevice.class);
		when(dev1.getValue()).thenReturn("2000");
		when(dev1.getName()).thenReturn(string[0]);
		when(dev1.getId()).thenReturn(string[0]);
		W1BaseDevice dev2 = mock(W1BaseDevice.class);
		when(dev2.getValue()).thenReturn("4800");
		when(dev2.getName()).thenReturn(string[1]);
		when(dev2.getId()).thenReturn(string[1]);

		return Arrays.asList(dev1, dev2);
	}
	
	private List<TemperatureSensor> aListOf1Sensor(String... string) throws IOException {
		TemperatureSensor dev1 = new TemperatureSensor();
		dev1.setUuid(string[0]);
		dev1.setId(1L);
		dev1.setPinState(DeviceStatus.PLUGGED);
		return Arrays.asList(dev1);
	}
	

}
