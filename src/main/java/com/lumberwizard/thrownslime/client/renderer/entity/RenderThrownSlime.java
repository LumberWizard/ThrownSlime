package com.lumberwizard.thrownslime.client.renderer.entity;

import javax.annotation.Nonnull;

import com.lumberwizard.thrownslime.entity.EntityThrownSlime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderThrownSlime extends Render<EntityThrownSlime> {

	protected final Item item;
    private final RenderItem itemRenderer;
	
	public RenderThrownSlime(RenderManager renderManagerIn, Item itemIn, RenderItem itemRendererIn) {
		super(renderManagerIn);
		item = itemIn;
		itemRenderer = itemRendererIn;
	}
	
	public void doRender(EntityThrownSlime entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public ItemStack getStackToRender(EntityThrownSlime entityIn)
    {
        return new ItemStack(this.item);
    }

	private ResourceLocation texture = new ResourceLocation("items/SLIME_BALL.png");

	public static final Factory FACTORY = new Factory();
	
	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityThrownSlime entity) {
		return texture;
	}

	public static class Factory implements IRenderFactory<EntityThrownSlime> {

		@Override
		public Render<? super EntityThrownSlime> createRenderFor(RenderManager manager) {
			return new RenderThrownSlime(manager, Items.SLIME_BALL, Minecraft.getMinecraft().getRenderItem());
		}

	}

}
