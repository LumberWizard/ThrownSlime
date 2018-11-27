package dev.lumberwizard.thrownslime.entity;

import dev.lumberwizard.thrownslime.ModConfig;
import dev.lumberwizard.thrownslime.ObjectHolders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrownSlime extends EntitySnowball {

	private static final DataParameter<Integer> ITEM = EntityDataManager.<Integer>createKey(EntityThrownSlime.class,
			DataSerializers.VARINT);

	public EntityThrownSlime(World world) {
		super(world);
		this.dataManager.register(ITEM, Item.getIdFromItem(Items.SLIME_BALL) << 4);
	}

	public EntityThrownSlime(World worldIn, EntityLivingBase throwerIn, ItemStack item) {
		super(worldIn, throwerIn);
		this.dataManager.register(ITEM, Item.getIdFromItem(item.getItem()) << 4 | item.getMetadata());
	}

	public EntityThrownSlime(World worldIn, double x, double y, double z, ItemStack item)
    {
        super(worldIn, x, y, z);
		this.dataManager.register(ITEM, Item.getIdFromItem(item.getItem()) << 4 | item.getMetadata());
    }

	public ItemStack getSlimeball() {
		return new ItemStack(Item.getItemById(dataManager.get(ITEM) >>> 4), 1, dataManager.get(ITEM) & 0b1111);
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("item", dataManager.get(ITEM));
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
		dataManager.register(ITEM, compound.getInteger("item"));
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                world.spawnParticle(EnumParticleTypes.SLIME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Override
    protected void onImpact(RayTraceResult result)
    {
        Entity entity = result.entityHit;
        
		if (entity != null && !(entity instanceof EntitySlime)) {
			int i = 0;
			if (!world.isRemote && entity instanceof EntityLivingBase && this.ticksExisted > 0
					&& (!(entity instanceof EntityPlayer) || ModConfig.affectCreative
							|| !((EntityPlayer) entity).capabilities.isCreativeMode)) {
				((EntityLivingBase) entity).addPotionEffect(
						new PotionEffect(MobEffects.SLOWNESS, ModConfig.effectTime * 20, ModConfig.level - 1));
				Item item = getSlimeball().getItem();
				if (item == Items.MAGMA_CREAM) {
					entity.setFire(ModConfig.effectTime * 20);
					if (entity instanceof EntitySnowman) {
						i = 3;
					}
				}
				if (Loader.isModLoaded("industrialforegoing") && item == ObjectHolders.pinkSlime) {
					((EntityLivingBase) entity).addPotionEffect(
							new PotionEffect(MobEffects.GLOWING, ModConfig.effectTime * 20, ModConfig.level - 1));
				}
				entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
			}
		}

		if (entity != null && !(entity instanceof EntitySlime))
        {
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
			if (entity instanceof EntityLivingBase && !world.isRemote
					&& (this.getThrower() == null || !this.getThrower().isEntityEqual(entity))
					&& (!(entity instanceof EntityPlayer) || ModConfig.affectCreative
							|| !((EntityPlayer) entity).capabilities.isCreativeMode)) {
			}
        }

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }

}
