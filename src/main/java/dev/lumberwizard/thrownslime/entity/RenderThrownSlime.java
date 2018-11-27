package dev.lumberwizard.thrownslime.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderThrownSlime extends RenderSnowball<EntityThrownSlime> {

	public static final Factory FACTORY = new Factory();

	public RenderThrownSlime(RenderManager renderManagerIn, RenderItem itemRendererIn) {
		super(renderManagerIn, Items.SLIME_BALL, itemRendererIn);
	}


	@Override
	public ItemStack getStackToRender(EntityThrownSlime entity) {
		return entity.getSlimeball();
	}

	public static class Factory implements IRenderFactory<EntityThrownSlime> {

		@Override
		public Render<? super EntityThrownSlime> createRenderFor(RenderManager manager) {
			return new RenderThrownSlime(manager, Minecraft.getMinecraft().getRenderItem());
		}

	}

}
