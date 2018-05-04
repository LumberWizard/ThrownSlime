package com.lumberwizard.thrownslime;

import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.Config;

@Config(modid = ModThrownSlime.MODID, name = "Thrown Slime")
public class ModConfig {

	@Comment(value = "Set how long the slowness effect should last (in seconds)")
	@RangeInt(min = 1, max = 300)
	public static int slownessTime = 5;
	@Comment(value = "Set the level of the slowness effect")
	@RangeInt(min = 1, max = 3)
	public static int level = 1;
	@Comment("Set how long the fire should last (in seconds)")
	@RangeInt(min = 1, max = 300)
	public static int fireTime = 5;
	@Comment(value = "Set whether or not players in creative mode are affected by having slime balls thrown at them")
	public static boolean affectCreative = false;

}
