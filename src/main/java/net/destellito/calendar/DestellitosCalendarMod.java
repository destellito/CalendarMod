package net.destellito.calendar;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestellitosCalendarMod implements ModInitializer {
	public static final String MOD_ID = "destellitocalendarmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		GetDateCommand.register();
		LOGGER.info("DCMod initalized");
	}
}