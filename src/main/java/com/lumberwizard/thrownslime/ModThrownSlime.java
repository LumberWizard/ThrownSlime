package com.lumberwizard.thrownslime;

import org.apache.logging.log4j.Logger;

import com.lumberwizard.thrownslime.entity.EntityThrownMagma;
import com.lumberwizard.thrownslime.entity.EntityThrownSlime;
import com.lumberwizard.thrownslime.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(name = ModThrownSlime.MOD_NAME, modid = ModThrownSlime.MODID, version = ModThrownSlime.VERSION)
public class ModThrownSlime {

	public static final String MOD_NAME = "Thrown Slime";
	public static final String MODID = "thrownslime";
	public static final String VERSION = "1.2.1";

	public static Logger logger;

	@Mod.Instance
	public static ModThrownSlime instance;

	@SidedProxy(clientSide = "com.lumberwizard.thrownslime.proxy.ClientProxy", serverSide = "com.lumberwizard.thrownslime.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		int id = 0;
		EntityRegistry.registerModEntity(new ResourceLocation("items/SLIME_BALL.png"), EntityThrownSlime.class,
				"Thrown Slime", ++id, ModThrownSlime.instance, 64, 10, true);
		EntityRegistry.registerModEntity(new ResourceLocation("items/MAGMA_CREAM.png"), EntityThrownMagma.class,
				"Thrown Magma", ++id, ModThrownSlime.instance, 64, 10, true);
		proxy.preInit(event);
		proxy.registerRenderers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
