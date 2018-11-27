package dev.lumberwizard.thrownslime.proxy;

import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import dev.lumberwizard.thrownslime.entity.RenderThrownSlime;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownSlime.class, RenderThrownSlime.FACTORY);
	}

}
