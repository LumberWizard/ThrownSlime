package dev.lumberwizard.thrownslime;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = ModThrownSlime.MODID, name = "Thrown Slime")
public class ModConfig {

	@Comment(value = "Set how long an effect from a slimeball should last (in seconds)")
	@RangeInt(min = 1, max = 300)
	public static int effectTime = 5;
	@Comment(value = "Set the level of the slowness effect")
	@RangeInt(min = 1, max = 3)
	public static int level = 1;
	@Comment(value = "Set whether or not players in creative mode are affected by having slime balls thrown at them")
	public static boolean affectCreative = false;

}
