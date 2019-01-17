package dev.lumberwizard.thrownslime;

import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber
public class EventHandlers {

	protected static Random itemRand = new Random();

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		ItemStack item = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		if (!ModThrownSlime.balls.contains(Pair.of(item.getItem(), item.getMetadata())))
			return;

		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
				SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			ItemStack forSlime = item.copy();
			forSlime.setCount(1);
			EntityThrownSlime entitySlime = new EntityThrownSlime(world, player, forSlime);
			entitySlime.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entitySlime);
		}
		if (!player.capabilities.isCreativeMode) {
			item.shrink(1);
			if (player.getHeldItemMainhand().isEmpty()) {
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			}
			if (player.getHeldItemOffhand().isEmpty()) {
				player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
			}
		}
	}
	
	@SubscribeEvent
	public static void registerEntites(RegistryEvent.Register<EntityEntry> event) {
		EntityRegistry.registerModEntity(new ResourceLocation("thrownslime:thrown_slime"), EntityThrownSlime.class,
				"thrown_slime", 1, ModThrownSlime.instance, 64, 10, true);
	}

	@SubscribeEvent
	public static void OnConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ModThrownSlime.MODID)) {
			ConfigManager.sync(ModThrownSlime.MODID, Config.Type.INSTANCE);
		}
	}

}
