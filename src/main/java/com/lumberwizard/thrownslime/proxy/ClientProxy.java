package com.lumberwizard.thrownslime.proxy;

import com.lumberwizard.thrownslime.client.renderer.entity.RenderThrownMagma;
import com.lumberwizard.thrownslime.client.renderer.entity.RenderThrownSlime;
import com.lumberwizard.thrownslime.entity.EntityThrownMagma;
import com.lumberwizard.thrownslime.entity.EntityThrownSlime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownSlime.class, RenderThrownSlime.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownMagma.class, RenderThrownMagma.FACTORY);
	}

}
