package com.lumberwizard.thrownslime.proxy;

import java.io.File;

import com.lumberwizard.thrownslime.ModConfig;
import com.lumberwizard.thrownslime.entity.EntityThrownMagma;
import com.lumberwizard.thrownslime.entity.EntityThrownSlime;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
	    registerDispenserBehaviors();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

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
