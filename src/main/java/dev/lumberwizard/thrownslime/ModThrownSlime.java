package dev.lumberwizard.thrownslime;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;

import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod(name = ModThrownSlime.MOD_NAME, modid = ModThrownSlime.MODID, version = ModThrownSlime.VERSION)
public class ModThrownSlime {

	public static final String MOD_NAME = "Thrown Slime";
	public static final String MODID = "thrownslime";
	public static final String VERSION = "2.0.0";

	public static Logger logger;

	public static Set<Pair<Item, Integer>> balls = new HashSet<Pair<Item, Integer>>();

	@Mod.Instance
	public static ModThrownSlime instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		balls.add(Pair.of(Items.SLIME_BALL, 0));
		balls.add(Pair.of(Items.MAGMA_CREAM, 0));
		if (Loader.isModLoaded("industrialforegoing")) {
			balls.add(Pair.of(PINK_SLIME, 0));
		}
		registerDispenserBehaviors();
	}
	
	private void registerDispenserBehaviors() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SLIME_BALL, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntityThrownSlime(worldIn, position.getX(), position.getY(), position.getZ(),
						new ItemStack(Items.SLIME_BALL));
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.MAGMA_CREAM, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntityThrownSlime(worldIn, position.getX(), position.getY(), position.getZ(),
						new ItemStack(Items.MAGMA_CREAM));
			}
		});
		if (Loader.isModLoaded("industrialforegoing")) {
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(PINK_SLIME,
					new BehaviorProjectileDispense() {
						protected IProjectile getProjectileEntity(World worldIn, IPosition position,
								ItemStack stackIn) {
							return new EntityThrownSlime(worldIn, position.getX(), position.getY(), position.getZ(),
									new ItemStack(PINK_SLIME));
						}
					});
		}
		
	}
	
	@ObjectHolder("industrialforegoing:pink_slime")
	public static final Item PINK_SLIME = null;

}
