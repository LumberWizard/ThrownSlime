package dev.lumberwizard.thrownslime.entity;

import dev.lumberwizard.thrownslime.ModConfig;
import dev.lumberwizard.thrownslime.ModThrownSlime;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrownSlime extends EntitySnowball implements IEntityAdditionalSpawnData, IThrowableEntity {

	private ItemStack slimeball;
	private int age;

	public EntityThrownSlime(World world) {
		super(world);
		slimeball = new ItemStack(Items.SLIME_BALL);
		age = 0;
	}

	public EntityThrownSlime(World worldIn, EntityLivingBase throwerIn, ItemStack item) {
		super(worldIn, throwerIn);
		slimeball = item;
		age = 0;
	}

	public EntityThrownSlime(World worldIn, double x, double y, double z, ItemStack item) {
		super(worldIn, x, y, z);
		slimeball = item;
		age = 0;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		age++;
	}

	public ItemStack getSlimeball() {
		return slimeball;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				world.spawnParticle(EnumParticleTypes.SLIME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		Entity hitEntity = result.entityHit;
		if (age <= 2 && hitEntity.isEntityEqual(getThrower())) return;
		if (hitEntity == null) {
			if (!world.isRemote) {
				this.world.setEntityState(this, (byte) 3);
				this.setDead();
			}
			return;
		}
		Item slimeballItem = getSlimeball().getItem();
		if (!world.isRemote) {
			if ((ModConfig.affectCreative
					|| !(hitEntity instanceof EntityPlayer && ((EntityPlayer) hitEntity).capabilities.isCreativeMode))) {
				if (slimeballItem == Items.SLIME_BALL && hitEntity instanceof EntityLivingBase
						&& !(hitEntity instanceof EntitySlime)) {
					((EntityLivingBase) hitEntity).addPotionEffect(
							new PotionEffect(MobEffects.SLOWNESS, ModConfig.effectTime * 20, ModConfig.level - 1));
				} else if (slimeballItem == Items.MAGMA_CREAM && !hitEntity.isImmuneToFire()) {
					hitEntity.setFire(ModConfig.effectTime);
				} else if (Loader.isModLoaded("industrialforegoing") && slimeballItem == ModThrownSlime.PINK_SLIME
						&& hitEntity instanceof EntityLivingBase && !EntityList.isMatchingName(hitEntity,
								new ResourceLocation("industrialforegoing", "pink_slime"))) {
					((EntityLivingBase) hitEntity).addPotionEffect(
							new PotionEffect(MobEffects.GLOWING, ModConfig.effectTime * 20, ModConfig.level - 1));
				}
			}

			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
		if (!(slimeballItem == Items.MAGMA_CREAM && hitEntity.isImmuneToFire())
				&& !(slimeballItem == Items.SLIME_BALL && hitEntity instanceof EntitySlime)
				&& !(Loader.isModLoaded("industrialforegoing") && slimeballItem == ModThrownSlime.PINK_SLIME
						&& EntityList.isMatchingName(hitEntity,
								new ResourceLocation("industrialforegoing", "pink_slime"))))
			hitEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),
					slimeballItem == Items.MAGMA_CREAM && hitEntity instanceof EntitySnowman ? 3 : 0);
	}

	@Override
	public void setThrower(Entity entity) {
		if (entity instanceof EntityLivingBase)
			this.thrower = (EntityLivingBase) entity;
		else
			thrower = null;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeItemStack(buffer, slimeball);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		slimeball = ByteBufUtils.readItemStack(additionalData);
	}

}
