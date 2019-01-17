package dev.lumberwizard.thrownslime.render;

import dev.lumberwizard.thrownslime.ModThrownSlime;
import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import dev.lumberwizard.thrownslime.entity.RenderThrownSlime;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ModThrownSlime.MODID, value = Side.CLIENT)
public class RenderEventHandler {

	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownSlime.class, RenderThrownSlime.FACTORY);
	}
	
}
