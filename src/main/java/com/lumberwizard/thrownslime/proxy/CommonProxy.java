package com.lumberwizard.thrownslime.proxy;

import java.io.File;

import com.lumberwizard.thrownslime.Config;
import com.lumberwizard.thrownslime.ModThrownSlime;
import com.lumberwizard.thrownslime.entity.EntityThrownMagma;
import com.lumberwizard.thrownslime.entity.EntityThrownSlime;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent event) {
		File directory = event.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "ThrownSlime.cfg"));
		Config.readConfig();
		registerDispenserBehaviors();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public void registerRenderers() {

	}
	
	public void registerDispenserBehaviors() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SLIME_BALL, new BehaviorProjectileDispense()
        {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
            {
                return new EntityThrownSlime(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.MAGMA_CREAM, new BehaviorProjectileDispense()
        {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
            {
                return new EntityThrownMagma(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
	}

}
