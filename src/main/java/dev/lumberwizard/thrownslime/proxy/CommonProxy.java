package dev.lumberwizard.thrownslime.proxy;

import org.apache.commons.lang3.tuple.Pair;

import dev.lumberwizard.thrownslime.ModThrownSlime;
import dev.lumberwizard.thrownslime.ObjectHolders;
import dev.lumberwizard.thrownslime.entity.EntityThrownSlime;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModThrownSlime.balls.add(Pair.of(Items.SLIME_BALL, 0));
		ModThrownSlime.balls.add(Pair.of(Items.MAGMA_CREAM, 0));
		if (Loader.isModLoaded("industrialforegoing")) {
			ModThrownSlime.balls.add(Pair.of(ObjectHolders.pinkSlime, 0));
		}
		registerDispenserBehaviors();
	}

	public void registerDispenserBehaviors() {
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
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ObjectHolders.pinkSlime,
					new BehaviorProjectileDispense() {
						protected IProjectile getProjectileEntity(World worldIn, IPosition position,
								ItemStack stackIn) {
							return new EntityThrownSlime(worldIn, position.getX(), position.getY(), position.getZ(),
									new ItemStack(ObjectHolders.pinkSlime));
						}
					});
		}
	}

}
