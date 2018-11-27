package dev.lumberwizard.thrownslime;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;

import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import dev.lumberwizard.thrownslime.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(name = ModThrownSlime.MOD_NAME, modid = ModThrownSlime.MODID, version = ModThrownSlime.VERSION)
public class ModThrownSlime {

	public static final String MOD_NAME = "Thrown Slime";
	public static final String MODID = "thrownslime";
	public static final String VERSION = "2.0.0";

	public static Logger logger;

	public static List<Pair<Item, Integer>> balls = new ArrayList<Pair<Item, Integer>>();

	@Mod.Instance
	public static ModThrownSlime instance;

	@SidedProxy(clientSide = "dev.lumberwizard.thrownslime.proxy.ClientProxy", serverSide = "dev.lumberwizard.thrownslime.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		EntityRegistry.registerModEntity(new ResourceLocation("thrownslime:thrown_slime"), EntityThrownSlime.class,
				"Thrown Slime", 1, ModThrownSlime.instance, 64, 10, true);
		proxy.preInit(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
