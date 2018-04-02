package net.brewspberry.tests.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.front.ws.impl.RESTTemperatureService;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;
import net.brewspberry.tests.config.ApiSpringTestConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApiSpringTestConfiguration.class, SpringCoreTestConfiguration.class})
@WebAppConfiguration
public class RestTemperatureWSTest {
	
	
	RESTTemperatureService temperatureService = new RESTTemperatureService();
	
	@Test
	public void mergeJSONArrayTest () throws JSONException{
		
		
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		
		
		json.put("id", 1);
		json.put("uuid","1ab2c3d4e5");
		json.put("date", new Date());
		json.put("name", "RELAY1");
		json.put("brew", 1);
		json.put("step", 1);
		json.put("temp", 123);
		

		json2.put("id", 2);
		json2.put("uuid","1ab2c3d4e5");
		json2.put("date", new Date(123456));
		json2.put("name", "RELAY2");
		json2.put("brew", 1);
		json2.put("step", 1);
		json2.put("temp", 100);
				
		
		JSONArray array = new JSONArray();
		array.put(json);
		array.put(json2);
		

	}
	
	@Test
	public void shouldConvertListToJSONObject () throws JSONException{
		
		
		List<ConcreteTemperatureMeasurement> tmesList= new ArrayList<ConcreteTemperatureMeasurement>();
		

		ConcreteTemperatureMeasurement tm1 = new ConcreteTemperatureMeasurement();
		
		tm1.setTmes_actioner(new Actioner());
		tm1.setTmes_id(1L);
		tm1.setTmes_date(new Date());
		tm1.setTmes_brassin(new Brassin());
		tm1.setTmes_etape(new Etape());
		tm1.setTmes_probeUI("a1b2c3");
		tm1.setTmes_probe_name("RELAY1");

		ConcreteTemperatureMeasurement tm2 = new ConcreteTemperatureMeasurement();
		
		tm2.setTmes_actioner(new Actioner());
		tm2.setTmes_date(new Date());
		tm2.setTmes_brassin(new Brassin());
		tm2.setTmes_etape(new Etape());
		tm2.setTmes_id(2L);
		tm2.setTmes_probeUI("azerty");
		tm2.setTmes_probe_name("RELAY2");

		tmesList.add(tm1);
		tmesList.add(tm2);
		
//		JSONObject res = temperatureService.convertListToJSONObject(tmesList);
//		
//		
//		Assert.assertEquals(1, ((JSONArray) ((JSONObject) res.get(ConcreteTemperatureMeasurement.class.getSimpleName())).get("azerty")).length());
//		Assert.assertEquals("RELAY2", ((JSONObject) ((JSONArray) ((JSONObject) res.get(ConcreteTemperatureMeasurement.class.getSimpleName())).get("azerty")).get(0)).get("name"));
	}

}
