package com.lumberwizard.thrownslime;

import java.util.Random;

import com.lumberwizard.thrownslime.entity.EntityThrownMagma;
import com.lumberwizard.thrownslime.entity.EntityThrownSlime;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlers {

	protected static Random itemRand = new Random();

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		ItemStack item = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		if (!(item.getItem().equals(Items.SLIME_BALL) || item.getItem().equals(Items.MAGMA_CREAM)))
			return;
		if (!player.capabilities.isCreativeMode) {
			item.shrink(1);
			if (player.getHeldItemMainhand().isEmpty()) {
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			}
			if (player.getHeldItemOffhand().isEmpty()) {
				player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
			}
		}

		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
				SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			if (item.getItem().equals(Items.SLIME_BALL)) {
				EntityThrownSlime entitySlime = new EntityThrownSlime(world, player);
				entitySlime.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntity(entitySlime);
			}
			else if (item.getItem().equals(Items.MAGMA_CREAM)) {
				EntityThrownMagma entityMagma = new EntityThrownMagma(world, player);
				entityMagma.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntity(entityMagma);
			}
		}
	}

	@SubscribeEvent
	public static void OnConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
		if (event.getModID().equals(ModThrownSlime.MODID)) {
			ConfigManager.sync(ModThrownSlime.MODID, Config.Type.INSTANCE);
		}
	}

}
