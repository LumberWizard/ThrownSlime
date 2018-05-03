package com.lumberwizard.thrownslime;

import org.apache.logging.log4j.Level;

import com.lumberwizard.thrownslime.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

public class Config {

	private static final String CATEGORY_GENERAL = "general";

	// This values below you can access elsewhere in your mod:
	public static int slownessTime = 5;
	public static int level = 1;
	public static int fireTime = 5;

	// Call this from CommonProxy.preInit(). It will create our config if it doesn't
	// exist yet and read the values if it does exist.
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e1) {
			ModThrownSlime.logger.log(Level.ERROR, "Problem loading config file!", e1);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		slownessTime = cfg.getInt("time", CATEGORY_GENERAL, 5, 1, 300,
				"Set how long the slowness effect should last (in seconds; between 1 and 300)");
		level = cfg.getInt("level", CATEGORY_GENERAL, 1, 1, 3,
				"Set the level of the slowness effect (between 1 and 3)");
		fireTime = cfg.getInt("fireTime", CATEGORY_GENERAL, 5, 1, 300,
				"Set how long the fire should last (in seconds; between 1 and 300)");
	}

}
