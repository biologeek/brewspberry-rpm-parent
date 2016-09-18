package net.brewspberry.util;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public abstract class Constants {

	public static String BLOND = "Blonde";
	public static String BRUNE = "Brune";
	public static String BLANCHE = "Blanche";
	public static String IPA = "Blonde";
	public static String ALE = "Blonde";
	public static String STOUT = "Blonde";
	public static String LAGER = "Blonde";

	public static int ACT_RUNNING = 10;
	public static int ACT_PAUSED = 5;
	public static int ACT_STOPPED = 1;
	public static int ACT_FAILED = 999;

	public static String ACT_DS18B20 = "1";
	public static String ACT_PUMP = "2";
	public static String ACT_ENGINE = "3";
	public static String PROJECT_ROOT_PATH = "/opt/tomcat/webapps";

	static {
		// public static String PROJECT_ROOT_PATH =
		// "/home/xavier/ownCloud/Projets/Brewhouse/Code";

		if (OSUtils.isWindows()) {
			PROJECT_ROOT_PATH = "D:/Profiles/xcaron/git";

		}
	}
	public static String BREW_PARENT = PROJECT_ROOT_PATH+"/brewspberry-rpm-parent";
	public static String BREW_VIEWER = BREW_PARENT+"/brewspberry-webapp";
	public static String BREW_API = BREW_PARENT+"/brewspberry-core";
	public static String BREW_BATCHES = BREW_PARENT+"/brewspberry-batches";
	public static String BREW_TEMP = BREW_PARENT+"/brewspberry-api";
	public static String BREW_CONF = BREW_PARENT+"/brewspberry-conf";

	public static String DEVICES_PROPERTIES = BREW_CONF + "/devices.properties";

	public static String CONFIG_PROPERTIES = BREW_CONF + "/config.properties";

	public static String BATCHES_PROPERTIES = BREW_CONF + "/batches.properties";

	public Constants() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Map<String, String> INGREDIENT_TYPES = new HashMap<String, String>();

	static {

		INGREDIENT_TYPES.put("malt", "Malt");
		INGREDIENT_TYPES.put("hop", "Hop");
		INGREDIENT_TYPES.put("yeast", "Yeast");

	}

	public static Map<String, String> CEREALS = new HashMap<String, String>();
	static {

		CEREALS.put("wheat", "Wheat");
		CEREALS.put("barley", "Barley");
		CEREALS.put("oat", "Oat");
		CEREALS.put("buckwheat", "Buckwheat");
		CEREALS.put("corn", "Corn");
		CEREALS.put("rye", "Rye");
		CEREALS.put("spelt", "Spelt");
		CEREALS.put("rice", "Rice");

	}

	public static Map<String, String> MALT_TYPE = new HashMap<String, String>();
	static {

		MALT_TYPE.put("white", "White");
		MALT_TYPE.put("blond", "Blond");
		MALT_TYPE.put("amber", "Amber");
		MALT_TYPE.put("red", "Red");
		MALT_TYPE.put("black", "Black");

	}

	public static Map<String, String> HOP_TYPE = new HashMap<String, String>();
	static {

		HOP_TYPE.put("0", "Bittering");
		HOP_TYPE.put("1", "Aromatic");
		HOP_TYPE.put("2", "Both");

	}

	public static final Map<String, String> BREW_STATUS = new HashMap<String, String>();

	static {
		BREW_STATUS.put("10", "Brassage");
		BREW_STATUS.put("20", "Fermentation");
		BREW_STATUS.put("30", "Garde");
		BREW_STATUS.put("40", "Embouteillage");
		BREW_STATUS.put("50", "Dégustation");
		BREW_STATUS.put("60", "Terminée");
	};

	public static final Map<String, String> ACTIONNER = new HashMap<String, String>();

	static {
		BREW_STATUS.put("1", "ds18b20");
		BREW_STATUS.put("2", "relay");
	};

	public static final Map<String, Pin> BREW_GPIO = new HashMap<String, Pin>();

	static {
		BREW_GPIO.put("11", RaspiPin.GPIO_00);
		BREW_GPIO.put("12", RaspiPin.GPIO_01);
		BREW_GPIO.put("13", RaspiPin.GPIO_02);
		BREW_GPIO.put("15", RaspiPin.GPIO_03);
		BREW_GPIO.put("16", RaspiPin.GPIO_04);
		BREW_GPIO.put("18", RaspiPin.GPIO_05);
		BREW_GPIO.put("22", RaspiPin.GPIO_06);
		BREW_GPIO.put("07", RaspiPin.GPIO_07);
		BREW_GPIO.put("03", RaspiPin.GPIO_08);
		BREW_GPIO.put("05", RaspiPin.GPIO_09);
		BREW_GPIO.put("24", RaspiPin.GPIO_10);
		BREW_GPIO.put("26", RaspiPin.GPIO_11);
		BREW_GPIO.put("19", RaspiPin.GPIO_12);
		BREW_GPIO.put("21", RaspiPin.GPIO_13);
		BREW_GPIO.put("23", RaspiPin.GPIO_14);

	};

	public static final Map<Pin, String> BREW_GPIO_TO_STR = new HashMap<Pin, String>();

	static {
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_00, "11");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_01, "12");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_02, "13");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_03, "15");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_04, "16");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_05, "18");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_06, "22");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_07, "07");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_08, "03");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_09, "05");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_10, "24");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_11, "26");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_12, "19");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_13, "21");
		BREW_GPIO_TO_STR.put(RaspiPin.GPIO_14, "23");

	};

	public static final String DS18B20_DEVICES_FOLDER = "/sys/bus/w1/devices";
	public static final String DS18B20_DIR_PATTERN = "28*";
	public static final String DS18B20_FILE_NAME = "w1_slave";
	public static final String[] WRITABLE_ENTITIES = { "FILE", "SQL", "ALL" };

}
